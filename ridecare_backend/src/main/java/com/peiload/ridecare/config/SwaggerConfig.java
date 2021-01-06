package com.peiload.ridecare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(newHashSet(("application/json")))
                .apiInfo(swaggerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.peiload.ridecare"))
                .paths(PathSelectors.any())
                .build().securitySchemes(newArrayList(apiKey()));
    }

    private ApiInfo swaggerApiInfo() {
        return new ApiInfoBuilder()
                .title("RideCare Backend API")
                .description("Documentation for the RideCare Backend API")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}