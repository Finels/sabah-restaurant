package com.fasteam.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 静态资源配置、跨域配置、权限控制
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    private static final Integer RESOURCE_CACHE_OPEN = 1;

    @Value("${enable.resource.cache:1}")
    private Integer enableResourceCache;  //默认打开

    @Value("${resource.cache.time:3600}")
    private Integer resourceCacheTime;

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (RESOURCE_CACHE_OPEN.equals(enableResourceCache)) {
            registry.addResourceHandler("/css/**").addResourceLocations("/res/css/").setCachePeriod(resourceCacheTime);
            registry.addResourceHandler("/fonts/**").addResourceLocations("/res/fonts/").setCachePeriod(resourceCacheTime);
            registry.addResourceHandler("/js/**").addResourceLocations("/res/js/").setCachePeriod(resourceCacheTime);
            registry.addResourceHandler("/images/**").addResourceLocations("/res/images/").setCachePeriod(resourceCacheTime);
            registry.addResourceHandler("/favicon.ico").addResourceLocations("/res/images/logo/favicon.ico").setCachePeriod(resourceCacheTime);
        } else {
            registry.addResourceHandler("/css/**").addResourceLocations("/res/css/");
            registry.addResourceHandler("/fonts/**").addResourceLocations("/res/fonts/");
            registry.addResourceHandler("/js/**").addResourceLocations("/res/js/");
            registry.addResourceHandler("/images/**").addResourceLocations("/res/images/");
        }
    }

    /**
     * 跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .maxAge(3600);
    }

    /**
     * 自定义权限控制拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    @ConditionalOnMissingBean
    public InternalResourceViewResolver defaultViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/page/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}