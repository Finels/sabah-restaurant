package com.fasteam.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.fasteam.system.dao")
//@ServletComponentScan
@ComponentScan(basePackages = {"com.fasteam.system","com.fasteam.common","com.fasteam.security"})
public class SystemApplication extends SpringBootServletInitializer {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean(name = "restOtherTemplate")
    public RestTemplate restOtherTemplate() {
        return restTemplateBuilder.build();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SystemApplication.class);
    }
}
