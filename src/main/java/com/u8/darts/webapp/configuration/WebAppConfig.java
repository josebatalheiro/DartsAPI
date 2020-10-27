package com.u8.darts.webapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer.favorPathExtension(false);
        configurer.useJaf(false);
        configurer.replaceMediaTypes(Collections.singletonMap("json", MediaType.APPLICATION_JSON_UTF8));
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(new AbstractHttpMessageConverter<InputStream>(MediaType.APPLICATION_OCTET_STREAM) {
            protected boolean supports(Class<?> clazz) {
                return InputStream.class.isAssignableFrom(clazz);
            }

            protected InputStream readInternal(Class<? extends InputStream> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                return inputMessage.getBody();
            }

            protected void writeInternal(InputStream inputStream, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                //TODO handle this case
            }
        });

        super.configureMessageConverters(converters);
    }
}