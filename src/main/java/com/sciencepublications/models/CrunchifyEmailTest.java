package com.sciencepublications.models;

import com.sciencepublications.MailMail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CrunchifyEmailTest {

	public static void main( String[] args )
	{
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Mail.xml");



	}
}