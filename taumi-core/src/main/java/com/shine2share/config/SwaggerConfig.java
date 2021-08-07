package com.shine2share.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String AUTH_SERVER = "";
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "secret";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securitySchema1()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("taumi microservice")
                .description("taumi backend microservice rest api")
                .license("Apache License")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("shine2share", "https://www.facebook.com/shine2share", "thelam92@gmail.com"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(Collections.singletonList(securityReference())).build();
    }
    private SecurityReference securityReference() {
        return new SecurityReference("api_key", new AuthorizationScope[0]);
    }
    private SecurityScheme securitySchema1() {
        return new ApiKey("api_key", "Authorization", "Header");
    }
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }
}
