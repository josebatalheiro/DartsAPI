package com.u8.darts.webapp.configuration;

import com.u8.darts.utils.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/api/v1")
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {

        Properties properties = new Properties();
        try {
            properties.load(FileUtils.getFileInputStreamFromClasspath("version.properties"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return new ApiInfo(
                "Unit8 - Darts API",
                null,
                "v" + properties.get("app.version"),
                null,
                new Contact("Unit8", "http://www.unit8.co", null),
                null, null
        );
    }
}
