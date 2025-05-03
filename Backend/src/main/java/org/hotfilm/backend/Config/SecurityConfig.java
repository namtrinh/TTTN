package org.hotfilm.backend.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] POST_PUBlIC = {
            "/auth/verify_code",
            "/auth/reset-password",
            "/auth/forgot-password",
            "/auth/refresh",
            "/auth/login",
            "/auth/register",
            "/movie/**",
            "/room/**",
            "/api/**",
            "/email/**"
    };

    private final String[] GET_PUBLIC = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/movie/**",
            "/movie",
            "/room/**",
            "/room",
            "/showtime",
            "/showtime/**",
            "/seat",
            "/seat/**",
            "/api/**",
            "/order",
            "/order/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(POST_PUBlIC)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, GET_PUBLIC)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//                ).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.oauth2Login(oauth2 -> oauth2
//                .defaultSuccessUrl("http://localhost:4200/home", true)
//                .failureUrl("/auth/login?error=true")
//                .userInfoEndpoint(userInfo -> userInfo
//                        .userService(oauth2Service.oauth2UserService())
//                )

        //Config resource server để xử lí các yêu cầu oauth2 bearer token
        //Chỉ định dùng jwt để làm định dạng của token
//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
//                        .decoder(customJwtDecoder)
//                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

    // ánh xạ các thông tin từ JWT
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            String scope = jwt.getClaim("scope");
//            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + scope));
//        });
//        return jwtAuthenticationConverter;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
