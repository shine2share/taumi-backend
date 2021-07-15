package com.shine2share.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(
                "/generate-token",
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/swagger-ui.html#!/**",
                "/v2/**"
        ).permitAll().anyRequest().authenticated();
    }
}
