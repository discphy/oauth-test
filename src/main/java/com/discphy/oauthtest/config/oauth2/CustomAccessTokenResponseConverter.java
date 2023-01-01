package com.discphy.oauthtest.config.oauth2;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nimbusds.jose.util.JSONObjectUtils.getLong;
import static com.nimbusds.jose.util.JSONObjectUtils.getString;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
import static org.springframework.util.StringUtils.delimitedListToStringArray;

public class CustomAccessTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES = Stream.of(
			ACCESS_TOKEN,
			TOKEN_TYPE,
			EXPIRES_IN,
			REFRESH_TOKEN,
			SCOPE).collect(Collectors.toSet());

	@Override
	public OAuth2AccessTokenResponse convert(Map<String, Object> source) {
        try {
            return OAuth2AccessTokenResponse.withToken(getString(source, ACCESS_TOKEN))
                    .tokenType(getTokenType())
                    .expiresIn(getLong(source, EXPIRES_IN))
                    .scopes(getScope(source))
                    .additionalParameters(getAdditionalParameters(source))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

	private OAuth2AccessToken.TokenType getTokenType() {
		return OAuth2AccessToken.TokenType.BEARER;
	}

	private Set<String> getScope(Map<String, Object> source) throws ParseException {
		return source.containsKey(SCOPE) ? getScopeSet(source) : Collections.emptySet();
	}

	private Set<String> getScopeSet(Map<String, Object> source) throws ParseException {
		return Arrays.stream(delimitedListToStringArray(getString(source, SCOPE), " "))
				.collect(Collectors.toSet());
	}

	private Map<String, Object> getAdditionalParameters(Map<String, Object> source) {
		Map<String, Object> additionalParameters = new LinkedHashMap<>();

		source.entrySet().stream()
				.filter(e -> !TOKEN_RESPONSE_PARAMETER_NAMES.contains(e.getKey()))
				.forEach(e -> additionalParameters.put(e.getKey(), e.getValue()));

		return additionalParameters;
	}
}
