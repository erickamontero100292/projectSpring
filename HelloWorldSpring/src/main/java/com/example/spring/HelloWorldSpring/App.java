package com.example.spring.HelloWorldSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.spring.HelloWorldSpring.dto.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 ApplicationContext appContext = new ClassPathXmlApplicationContext("com/example/xml/beans.xml");
         Person m = (Person) appContext.getBean("person");

         System.out.println("Name: "+m.getName() + "( edad: " + m.getAge() + ")");

         ((ConfigurableApplicationContext) appContext).close();

    }
}
