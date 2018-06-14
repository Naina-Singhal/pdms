/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.config;

import com.pdms.interceptors.RequestHandingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

/**
 *
 * @author hpasupuleti
 */
@Configuration
@ComponentScan(basePackages = "com.pdms")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter{
    
    @Bean
    public ViewResolver getViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean
    public ViewResolver getBundleViewResolver()
    {
        ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
        resolver.setOrder(1);
        resolver.setBasename("views");
        return resolver;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        
        return resolver;
    }
        
    @Bean
    public RequestHandingInterceptor getRequestHandingInterceptor()
    {
        return new RequestHandingInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        RequestHandingInterceptor reqIntercept = new RequestHandingInterceptor();
//        reqIntercept.setUserLoginDAO(new UserLoginDAOImpl());
        registry.addInterceptor(getRequestHandingInterceptor());
    }
    
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer
//                .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
//                .parameterName("type")
//                .favorParameter(true)
//                .ignoreUnknownPathExtensions(false)
//                .ignoreAcceptHeader(false)
//                .useJaf(true)
//                .mediaType("xml", MediaType.APPLICATION_XML)
//                .mediaType("json", MediaType.APPLICATION_JSON);
//    }
//    
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.enableContentNegotiation(
//                new MappingJackson2XmlView(),
//                new MappingJackson2JsonView());
//    }
//    
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
