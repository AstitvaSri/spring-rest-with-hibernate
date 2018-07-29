package com.rest.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.cfg.Configuration;

import com.astitva.rest.entity.*;


@RestController
@RequestMapping("/zoroapi")
public class DemoRestController {
	private List<Students> theStudents;

	public static SessionFactory factory;
	
	@PostConstruct //To Load the data just once, not for every request
	public void loadDataInList() {
		theStudents = new ArrayList<>();
		
		theStudents.add(new Students("Astitva","Srivastava"));
		theStudents.add(new Students("Anuj","Mishra"));
		theStudents.add(new Students("Elon","Musk"));
	}
	
	@GetMapping("/students")
	public List<Students> getStudents() {
				
		return theStudents;
	}
	
	//*******************************SEND OTP**************************************
	
	///////////////////////////////////////////////////////////////////////
	@CrossOrigin("http://127.0.0.1:9999")
	@PostMapping("/test")
	public SendOtp testApi(@RequestBody ReadEmail user) {
		
		
				
		//Testing connection in case of hibernate failure to make sure sql driver is working
		/*
		try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/zorodb?useSSL=false", "root", "root");
				System.out.println("Connected");
				String sql="Insert into book(title, bookAuthor) values(?,?)";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, "Revolution 2020");
				ps.setString(2, "Chetan Bhagat");
				ps.executeUpdate();
				con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}*/
		
		//*
		//HIBERNATE
		//---------------------------------------------------------------------------------
		
		//creating session factory
		System.out.println("Starting to read hibernate config...");
			 factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class).buildSessionFactory();
			 
				System.out.println("DONE!");
		
		
		//creating session object to get current session
			Session session=factory.openSession();
		
		try {
		//create Annotated class POJO
			Book book=new Book("2 States","Chetan Bhagat");
			
		//start a transaction
			session.beginTransaction();
			
		//save the POJO
			session.save(book);
		//commit transaction
			session.getTransaction().commit();
		}
		finally {
			//close the session factory
			factory.close();
		}
			//*/
		
		
		
		
		
		
		
		//----------------------------------------------------------------------------------
		
		System.out.println("TESTING STARTED....");
		System.out.println(user.getFirstName()+" "+user.getLastName()+", "+user.getUsername()+", "+user.getPassword());
		System.out.println("TEST DONE!");
		SendOtp sendOtp=new SendOtp(user.getUsername(),"ABCD12",false);
		
		return sendOtp;
		
		
	}
	
		
	
	
}

