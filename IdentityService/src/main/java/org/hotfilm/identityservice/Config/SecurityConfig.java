package org.hotfilm.identityservice.Config;

import lombok.RequiredArgsConstructor;
import org.hotfilm.identityservice.ServiceImp.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private Oauth2Service oauth2Service;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    private final String[] POST_PUBlIC = {
            "/auth/verify_code",
            "/auth/reset-password",
            "/auth/forgot-password",
            "/auth/refresh",
            "/auth/login",
            "/auth/register",
            "/movie/**",
            "/room/**",
            "/showtime",
            "/showtime/**",
            "/api/**",
            "/seat/**"
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
            "/api/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(POST_PUBlIC)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, GET_PUBLIC)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                );
        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("http://localhost:4200/home", true)
                .failureUrl("/auth/login?error=true")
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oauth2Service.oauth2UserService())
                )
        );

        //Config resource server để xử lí các yêu cầu oauth2 bearer token
        //Chỉ định dùng jwt để làm định dạng của token
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        return http.build();
    }

    // ánh xạ các thông tin từ JWT
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String scope = jwt.getClaim("scope");
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + scope));
        });
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
