package com.example.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Spring의 설정 파일이라는 의미
public class WebConfig implements WebMvcConfigurer {

    private String resourcePath = "/upload/**"; // view에서 접근할 경로
    private String savePath = "file:///C:/springboot_img/"; // 실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("https://taskmanager-main-ekh8bbgpa3dcbvht.koreacentral-01.azurewebsites.net") // Azure 배포 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // 필요한 HTTP 메서드 허용
    }
}
