package com.pisight.pimoney;

import javax.servlet.Filter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
//@EntityScan(basePackages = { "com.pisight.pimoney.entities" })
//@ComponentScan(basePackages = "com.pisight.pimoney")
//@EntityScan("com.pisight.pimoney.entities")
//@EnableGemfireRepositories
public class ConjurerApisSb3xVersionApplication {

	@Autowired
	BeanFactory beanFactory;
	
	@Bean
	public FilterRegistrationBean<Filter> jwtFilter() {
		final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		registrationBean.setFilter((Filter) beanFactory.getBean("JwtFilter"));
		registrationBean.addUrlPatterns("/securek2/*");
		registrationBean.addUrlPatterns("/api/v1/everest/*");
		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(ConjurerApisSb3xVersionApplication.class, args);
		//ConfigurableApplicationContext ctx = SpringApplication.run(ConjurerApisSb3xVersionApplication.class, args);
	}

}
