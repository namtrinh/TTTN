package org.hotfilm.backend.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hotfilm.backend.Exception.AppException;
import org.hotfilm.backend.Exception.ErrorCode;
import org.hotfilm.backend.Mapper.UserMapper;
import org.hotfilm.backend.Model.InvalidateToken;
import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.ModelDTO.Request.*;
import org.hotfilm.backend.ModelDTO.Response.AuthenticateResponse;
import org.hotfilm.backend.ModelDTO.Response.CheckTokenResponse;
import org.hotfilm.backend.ModelDTO.Response.LoginResponse;
import org.hotfilm.backend.Repository.InvalidateTokenRepository;
import org.hotfilm.backend.Repository.UserRepository;
import org.hotfilm.backend.ServiceImp.VerifyCodeGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @NonFinal
    @Value("${jwt.secretKey}")
    public String SIGN_KEY;

    @NonFinal
    @Value("${jwt.valid-Duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-Duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;
    
/*    public String login(UserRequest userRequest) throws JOSEException {
         String tokenKey = null;
        if (!userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email or password is incorrect!");
        }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User customer = userRepository.findByEmail(userRequest.getEmail());
            boolean checkPassword = passwordEncoder.matches(userRequest.getPassword(), customer.getPassword());
            if (checkPassword) {
                tokenKey = this.generateToken(customer);
            }else {
                throw new RuntimeException("Password is incorrect!");
            }

        System.out.println(tokenKey);
        return tokenKey;
    }

 */
    public LoginResponse login(UserRequest userRequest) {
        User user= userRepository
                .findByEmail(userRequest.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(userRequest.getPassword(), user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.LOGIN_ACCOUNT);
        LocalDateTime now = LocalDateTime.now();
        if (user.getLastRequestTime() != null) {
            if (now.isBefore(user.getLastRequestTime().plusMinutes(10))) {
                if (user.getRequestCount() >= 5) {
                    throw new AppException(ErrorCode.REQUEST_LIMIT_EXCEEDED);
                }
            } else {
                user.setRequestCount(0);
            }
        }
        user.setRequestCount(user.getRequestCount() + 1);
        user.setLastRequestTime(now);

        String auth_code = VerifyCodeGeneration.generateVerificationCode();
        user.setVerificationCode(auth_code);
        user.setVerificationCodeExpiry(now.plusMinutes(1));

        userRepository.save(user);

        emailService.sendCodeToMail(user.getEmail(), "Mã xác nhận của bạn là: ", "Mã của bạn là: " + auth_code);
        return LoginResponse.builder().authenticate(true).build();
    }

    public String register(UserRequest userRequest){
        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new AppException(ErrorCode.ACCOUNT_EXIST);
        }
        User user = userMapper.toUser(userRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setActivated(false);
        userRepository.save(user);
        return "Ok";
    }

    public AuthenticateResponse verifyAuthCode(VerifyCodeRequest verifyCodeRequest) throws JOSEException {
        User user = userRepository.findByEmail(verifyCodeRequest.getEmail());
        // Kiểm tra mã xác thực
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(verifyCodeRequest.getAuthCode())) {
            throw new AppException(ErrorCode.VERIFY_CODE_EXPIRED);
        }
        if (user.getVerificationCodeExpiry() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiry())) {
            throw new AppException(ErrorCode.VERIFY_CODE_EXPIRED);
        }
        // Mã xác thực hợp lệ, tạo token
        var token = generateToken(user);
        // Xoá mã xác thực sau khi xác minh thành công
        user.setVerificationCode(null);
        user.setActivated(true);
        userRepository.save(user);
        return AuthenticateResponse.builder().token(token).authenticate(true).build();
    }

    public CheckTokenResponse checkToken(CheckTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return CheckTokenResponse.builder().valid(isValid).build();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = isRefresh
                ? Date.from(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS))
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.EXPIRE_TOKEN);
        }
        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }


    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            if (invalidateTokenRepository.existsById(jit)){
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidatedToken =
                    InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
            invalidateTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    public AuthenticateResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
            throws ParseException, JOSEException {
        var signJWT = verifyToken(refreshTokenRequest.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
        InvalidateToken invalidatedToken =
                InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidateTokenRepository.save(invalidatedToken);

        var userId = signJWT.getJWTClaimsSet().getSubject();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        var token = generateToken(user.get());
        return AuthenticateResponse.builder().token(token).authenticate(true).build();
    }

    public String generateToken(User customer) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(customer.getUserId())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + VALID_DURATION))
                .claim("scope", customer.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
        return jwsObject.serialize();
    }
}
