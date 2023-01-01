package com.discphy.oauthtest.config.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientName() = " + userRequest.getClientRegistration().getClientName());
        if (userRequest.getClientRegistration().getClientName().equalsIgnoreCase(OAuth2Provider.WEIBO.name())) {
            super.setRequestEntityConverter(new CustomRequestEntityConverter());
        }

        return super.loadUser(userRequest);
    }
}
