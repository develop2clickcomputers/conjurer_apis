package com.pisight.pimoney.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableJpaRepositories(basePackages="com.pisight.pimoney.repository", entityManagerFactoryRef="k2EntityManagerFactory")
@EnableTransactionManagement
public class K2DataSourceConfig {
	
	
	@Bean(name="k2DataSource")
	@Primary
	@ConfigurationProperties(prefix="datasource.k2")
	public DataSource k2DataSource() {
	    return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean k2EntityManagerFactory(
	        EntityManagerFactoryBuilder builder) {
	    return builder
	            .dataSource(k2DataSource())
	            .packages("com.pisight.pimoney.repository")
	            .persistenceUnit("k2EntityManager")
	            .build(); 
	}
	
	@Bean
	public EntityManager k2EntityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(k2EntityManagerFactory(builder).getObject());
		
		return txManager;
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/k2/**");
            }
        };
    }
}
