package com.discphy.oauthtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/error", "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
            .csrf().disable()
            .oauth2Login()
                .userInfoEndpoint()
                    //.oidcUserService(new CustomOAuth2UserService())
                    .and()
                .defaultSuccessUrl("/user")
                .failureHandler((request, response, exception) -> exception.printStackTrace());

        return http.build();
    }
}
