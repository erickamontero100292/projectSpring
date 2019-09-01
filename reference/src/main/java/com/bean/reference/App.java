package com.bean.reference;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bean.reference.dto.Room;
import com.bean.reference.dto.Window;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("com/reference/xml/bean.xml");
		Room room = (Room)appContext.getBean("roomFirst");
		
		if(room.getWindows().isEmpty()) {
			
			for(Window window : room.getWindows()) {
				System.out.println(window.toString());
			}
		}
		System.out.println(room.toString());
		((ConfigurableApplicationContext) appContext).close();
	}
}
