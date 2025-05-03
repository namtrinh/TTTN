package org.hotfilm.backend.Config;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hotfilm.backend.ModelDTO.Request.CheckTokenRequest;
import org.hotfilm.backend.ModelDTO.Response.CheckTokenResponse;
import org.hotfilm.backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, String> hashOperations;

    JwtAuthenticationFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    private static HashMap<String, String> cache = new HashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String secretKey = "1234567890123456";
        String HASHKEY_JWT  = "HASHKEY_JWT";

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    String tokenId = cookie.getValue();
                    try {
                        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

                        byte[] byteJwtId = Base64.getDecoder().decode(tokenId);

                        byte[] decoded = cipher.doFinal(byteJwtId);
                        String jwtId = new String(decoded);

                        token = hashOperations.get(HASHKEY_JWT, jwtId);

                        System.out.print("                               aaaaaaa        " + token);
                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                             IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        try {
            if (token != null && authService.isValidToken(token)) {
                String username = authService.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (ParseException | JOSEException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(request, response);
    }
}
