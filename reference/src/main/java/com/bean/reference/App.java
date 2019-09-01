package com.bean.reference;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bean.reference.dto.Room;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("com/reference/xml/bean.xml");
		Room room = (Room)appContext.getBean("roomFirst");
		System.out.println(room.toString());
		((ConfigurableApplicationContext) appContext).close();
	}
}
