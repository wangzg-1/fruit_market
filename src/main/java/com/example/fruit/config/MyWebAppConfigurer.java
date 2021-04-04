package com.example.fruit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/uploadImages/**").addResourceLocations("file:F:/temp/");
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/resources/")
//                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:/Users/wzg/IdeaProjects/fruit/src/main/resources/static/images/");
    }
}
