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

    public String privateKeyValue = "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";

    //   @Value("${jwt.publickey}")
    public String publicKeyValue = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA5iHmnOqKCgrtjJqqZv6NirKBa+3MVzF0Qo4YfTTjCaV3N4npV8D3n3CWpl+Ceq4qKdAi2Elw1RFFkTIcrCSWHS4VihVwT1fdQaMrWpfI7dvgnnLJTlD+bDk2pefA1u9uzoC8T+qrELkwD+Q6WJY5os9GVYb2I1gwFEPKAQU7rGgWnJNxNs3Y3FGTnlMLTFiNhKPuIR2X5OkEdlH/Y56jrmYFdrUArPdHaz7D48AW2IC+ZzSZzRwAR6AoTRZCAJ9e/XseoXDGEpLbVdiIh5F879E/Hq6z4vulhPFOU7F3dCIsPtDvOQ3iWsEdW6pHFmgm6CJ46pJXgbtLp50LtWGincT5XKWo0W1igiByFQC3/8G3dg40Llni+NYjVpzBFIxZoJ52xR7OYKL32icp5DivVdkHLbaMT/GszNQcMnVkvMZHKM1HkHmG414WZoNcA3vP42lJzqr5xJ57jJtxKk7Gn8G30dsfX3/uos99hjzeW3/AbKTJGhZHqgJZ8JoR5chdcv1aZRor1D0Xj9anPLI6yWqQf3C2VEkiZiABbVULnctbu3oGXXX3DvA7b9cvHM5HkBBRjruS7B7GFnT+dt07S1BKgPO4cy0g38iLKXg6cR1MxRJ1SUZeiRgi1EUy5K3YvxYhs55NWvBZfcfhlZDaGpwZcxEJn/5X/8mfHJFJ74ECAwEAAQ==";


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

    public boolean verifyAuthCode(VerifyCodeRequest verifyCodeRequest) throws JOSEException, NoSuchAlgorithmException, InvalidKeySpecException{
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
