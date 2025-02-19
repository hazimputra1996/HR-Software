package com.hr_software_project.hr_management.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hr_software_project.hr_management")
public class WebConfig implements WebMvcConfigurer {
    // You can add custom configurations here if needed
}

