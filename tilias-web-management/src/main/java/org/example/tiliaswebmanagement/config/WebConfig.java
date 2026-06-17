package org.example.tiliaswebmanagement.config;

import org.example.tiliaswebmanagement.Interceptor.DemoInterceptor;
import org.example.tiliaswebmanagement.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   /* @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private DemoInterceptor demoInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor)
                .addPathPatterns("/**");
    }*/
}
