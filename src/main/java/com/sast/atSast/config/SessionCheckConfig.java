package com.sast.atSast.config;

import com.sast.atSast.util.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author punkginger
 * @description 使用session判断是否登录
 */
@Configuration
public class SessionCheckConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new SessionInterceptor()).
                excludePathPatterns("/login").addPathPatterns("/stu/**","/message/**");//拦截的url由负责登录部分的朋友补全吧
    }
}

