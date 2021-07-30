package com.yccztt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private HttpInterceptor httpInterceptor;

    /**
     * 设置跨域
     * 使authorization在响应头中出现
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
                .exposedHeaders("authorization")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /**
     * 设置自定义的拦截器, 拦截所有界面
     * 排除 /login 请求
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/excHello","/login", "/error");
        super.addInterceptors(registry);
    }

}
