package com.shine2share.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${jwtPublicKey}")
    private String publicKey;
    private final AppAuthenticationEntryPoint appAuthenticationEntryPoint;

    public OAuth2ResourceServerConfig(AppAuthenticationEntryPoint appAuthenticationEntryPoint) {
        this.appAuthenticationEntryPoint = appAuthenticationEntryPoint;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable().authorizeRequests().antMatchers(
                "/generate-token",
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/swagger-ui.html#!/**",
                "/v2/**"
        ).permitAll().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenServices());
        resources.authenticationEntryPoint(appAuthenticationEntryPoint);
    }
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        jwtAccessTokenConverter.setSigningKey("mykey");
        return jwtAccessTokenConverter;
    }
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
