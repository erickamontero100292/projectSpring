package com.bean.properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bean.properties.connections.ConnectionBD;

public class App {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/bean/xml/beans.xml");

		ConnectionBD oracle = (ConnectionBD) applicationContext.getBean("oracle");
		ConnectionBD mySql = (ConnectionBD) applicationContext.getBean("mysql");

		System.out.println("ORACLE");
		System.out.println(oracle);
		System.out.println("MYSQL");
		System.out.println(mySql);
		((ConfigurableApplicationContext) applicationContext).close();

	}
}
