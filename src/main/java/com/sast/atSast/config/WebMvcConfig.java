package com.sast.atSast.config;

import com.sast.atSast.util.CustomJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author: Xander
 * @Date: 2021/4/20 14:27
 * @Description: Mvc配置
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private CustomJsonHttpMessageConverter converter;


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
}
