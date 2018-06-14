/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.config;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * @author hpasupuleti
 */
@Configuration
public class PropertyConfig {

    @Bean
    PropertyPlaceholderConfigurer propConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        //placeholderConfigurer.setLocation(new ClassPathResource("application.properties"));
        placeholderConfigurer.setLocations(new ClassPathResource("application.properties"),
                new ClassPathResource("dbconnection.properties"));
        return placeholderConfigurer;
    }

    @Bean
    Properties myProperties() throws IOException{
        Resource resource = new ClassPathResource("application.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        return props;
    }
    
    @Bean
    Properties JDBCProperties() throws IOException{
        Resource resource = new ClassPathResource("dbconnection.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        return props;
    }
}
