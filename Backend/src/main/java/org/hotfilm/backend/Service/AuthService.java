package org.hotfilm.backend.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    AuthService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    @NonFinal
    @Value("${jwt.valid-Duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-Duration}")
    protected long REFRESHABLE_DURATION;

    @Value("${jwt.privateKey}")
    public String privateKeyValue;

    @Value("${jwt.publickey}")
    public String publicKeyValue;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    private AuthenticationManager authenticationManager;

    public LoginResponse login(UserRequest userRequest) {
        User user = userRepository
                .findByEmail(userRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
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

    public String register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AppException(ErrorCode.ACCOUNT_EXIST);
        }
        User user = userMapper.toUser(userRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setActivated(false);
        userRepository.save(user);
        return "Ok";
    }

    public boolean verifyAuthCode(VerifyCodeRequest verifyCodeRequest) throws JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = userRepository.findByEmail(verifyCodeRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        // Kiểm tra mã xác thực
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(verifyCodeRequest.getAuthCode())) {
            throw new AppException(ErrorCode.VERIFY_CODE_EXPIRED);
        }
        if (user.getVerificationCodeExpiry() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiry())) {
            throw new AppException(ErrorCode.VERIFY_CODE_EXPIRED);
        }
        // Xoá mã xác thực sau khi xác minh thành công
        user.setVerificationCode(null);
        user.setActivated(true);
        userRepository.save(user);
        return true;
    }

    public boolean isValidToken(String jwt) throws ParseException, JOSEException {
        boolean isValid = true;
        try {
            verifyJWT(jwt);
        } catch (RuntimeException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            isValid = false;
        }
        return isValid;
    }

    public SignedJWT verifyJWT(String jwt) throws JOSEException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {

        // Load the public key using X509EncodedKeySpec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyValue));
        publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        // Verify the JWT
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);

        SignedJWT signedJWT = SignedJWT.parse(jwt);

        if (!userRepository.existsByEmail(signedJWT.getJWTClaimsSet().getSubject())) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        boolean verify = signedJWT.verify(jwsVerifier);

        if (!(verify && signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()))) {
            throw new RuntimeException("jwt Invalid");
        }
        return signedJWT;
    }

    public String generationJwt(User customer) throws JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.RS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(customer.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + VALID_DURATION))
                .claim("scope", customer.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // Load the private key using PKCS8EncodedKeySpec for signing
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyValue));
        privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        // Sign the JWT with the private key
        jwsObject.sign(new RSASSASigner(privateKey));
        System.out.print(jwsObject.serialize());
        return jwsObject.serialize();
    }

    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyValue));
        publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);
        if (!signedJWT.verify(jwsVerifier)) {
            throw new RuntimeException("cc");
        }
        return signedJWT.getJWTClaimsSet().getSubject();

    }
}
