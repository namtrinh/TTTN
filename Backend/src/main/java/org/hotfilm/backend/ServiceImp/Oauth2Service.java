package org.hotfilm.backend.ServiceImp;

import com.nimbusds.jose.JOSEException;
import org.hotfilm.backend.Model.User;
import org.hotfilm.backend.Repository.UserRepository;
import org.hotfilm.backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2Service {

    @Autowired
    private UserRepository customerRepository;

    @Autowired
    private AuthService authService;

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            String email = oAuth2User.getAttribute("email");

            this.responseToken(email, registrationId);
            System.out.println("Logged in with " + registrationId + ", email: " + email);
            return oAuth2User;
        };
    }

    public String responseToken(String email, String registrationId) {
        User customer = new User();
        customer.setEmail(email);
        customer.setOauth2Id(registrationId);
        customer.setRole(User.Role.USER);
        if (!customerRepository.existsByEmail(email)) {
            customerRepository.save(customer);
        }
        try {
            String token = authService.generateToken(customer);
            System.out.println(token);
            return token;
        } catch (JOSEException e) {
            throw new RuntimeException("error dcm");
        }
    }
}
