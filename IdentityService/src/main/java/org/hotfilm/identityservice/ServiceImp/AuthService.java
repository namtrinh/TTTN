package org.hotfilm.identityservice.ServiceImp;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.hotfilm.identityservice.Model.User;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${jwt.secretKey}")
    public String SIGN_KEY;
    @Autowired
    private UserRepository userRepository;
    
    public String login(UserRequest userRequest) throws JOSEException {
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

    public String generateToken(User customer) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(customer.getUserId())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 600000))
                .claim("scope", customer.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
        return jwsObject.serialize();
    }
}
