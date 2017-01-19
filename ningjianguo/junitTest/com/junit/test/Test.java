package com.junit.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
	@project:ningjianguo
	@author:Techape
	@date:2017年1月19日 下午3:36:34
	@email:1195726908@qq.com
	@version:v1.0
	@description:
*/
public class Test {
	private Session session;
	
	@Before
	public void createSession(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		try {
			this.session = sessionFactory.getCurrentSession();
			System.out.println(this.session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void testQuery(){
	}
	
	@After
	public void destroySession(){
		session.close();
	}
}


