package com.discphy.oauthtest.config.oauth2;

import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;

public class CustomRequestEntityConverter extends OAuth2UserRequestEntityConverter {

    @Override
    public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
        return RequestEntity.get(userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri(),
                userRequest.getAdditionalParameters().get("uid"),
                userRequest.getAccessToken().getTokenValue()).build();
    }
}
