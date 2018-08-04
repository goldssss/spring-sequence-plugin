package com.goldssss.sequence.core.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author majinxin1
 * @date 2018/8/4
 */
@Configuration
public class SequenceResourceAdapter extends WebMvcConfigurationSupport{
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/sequence/**").addResourceLocations("classpath:/sequence/");
        super.addResourceHandlers(registry);
    }
}
