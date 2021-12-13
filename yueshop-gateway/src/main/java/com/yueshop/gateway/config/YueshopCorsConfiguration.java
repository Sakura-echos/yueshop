package com.yueshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class YueshopCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

         CorsConfiguration corsConfiguration = new CorsConfiguration();

         //1、配置跨域
         corsConfiguration.addAllowedHeader("*");//请求头
         corsConfiguration.addAllowedMethod("*");//请求方式
         corsConfiguration.addAllowedOrigin("*");//请求来源
         corsConfiguration.setAllowCredentials(true);
         //任意路径都需要跨域配置
         source.registerCorsConfiguration("/**",corsConfiguration);
         return new CorsWebFilter(source);
    }
}