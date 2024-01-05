package com.pisight.pimoney.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("InstanceFactory")
public class InstanceFactory {
	
	@Autowired
    private BeanFactory beanFactory;
	
	private static BeanFactory beanFactoryStatic;
	
	@PostConstruct
	public void init() {
		beanFactoryStatic = beanFactory;
	}
	
	public static ResponseManager getResponseManagerInstance() {
		
		return (ResponseManager) beanFactoryStatic.getBean("responseManager");
		
	}
	
	
	public static Object getInstance(String name) {
		
		return beanFactoryStatic.getBean(name);
	}

}
