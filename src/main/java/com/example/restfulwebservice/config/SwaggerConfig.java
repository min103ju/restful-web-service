package com.example.restfulwebservice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // TODO: 2021-08-16 swagger 3.x.x로 넘어오면서 springfox-boot-starter 의존성만 추가하면 된다.
    // TODO: 2021-08-16 ui 확인 시 /swagger-ui/로 접속 시 api 페이지 확인 가능하다.

    private static final Contact DEFAULT_CONTACT = new Contact("Citizen", "http://www.naver.com",
        "email@address.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
        "Awesome API Title",
        "My User management REST API service",
        "1.0",
        "urn:tos",
        DEFAULT_CONTACT,
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES
        = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(DEFAULT_API_INFO)
            .produces(DEFAULT_PRODUCES_AND_CONSUMES)
            .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

}
