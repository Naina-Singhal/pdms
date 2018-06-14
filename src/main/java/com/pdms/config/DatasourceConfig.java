/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pdms.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Configuration
//@PropertySource(value = "classpath:dbconnection.properties",ignoreResourceNotFound = true)
public class DatasourceConfig {
    
    private DataSource datasource;
    private BasicDataSource basicDataSource;
    
    @Value("${jdbc.driver}")
    private String driver;
    
    @Value("${jdbc.user}")
    private String user;
    
    @Value("${jdbc.password}")
    private String password;
    
    @Value("${jdbc.url}")
    private String url;
     
    
    public DataSource getDataSource()
    {
        basicDataSource = new BasicDataSource();
//        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        basicDataSource.setUsername("root");
//        basicDataSource.setPassword("root");
//        basicDataSource.setUrl("jdbc:mysql://localhost:3306/pdms?autoReconnect=true");
//        
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        
        basicDataSource.setRemoveAbandoned(true);
        basicDataSource.setRemoveAbandonedTimeout(100);
        basicDataSource.setMaxActive(1000);
        basicDataSource.setMaxIdle(100);
        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxWait(10000);
        basicDataSource.setTestWhileIdle(true);
        basicDataSource.setTestOnReturn(true);
        basicDataSource.setTimeBetweenEvictionRunsMillis(10000);
        basicDataSource.setMinEvictableIdleTimeMillis(20000);
        
        datasource = basicDataSource;
        
        //setDatasource(basicDataSource);
        return datasource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDatasource());
    }
    
    
    @Bean
    public JdbcTemplate getJdbcTemplate()
    {
        return new JdbcTemplate(getDatasource());
    }
    
    /**
     * @return the datasource
     */
    @Bean
    public DataSource getDatasource() {
        return getDataSource();
    }
    
    @Bean
    public TransactionTemplate getTransactionTemplate() {
        return new TransactionTemplate(txManager());
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * @return the basicDataSource
     */
    public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    /**
     * @param basicDataSource the basicDataSource to set
     */
    public void setBasicDataSource(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    
    
}
