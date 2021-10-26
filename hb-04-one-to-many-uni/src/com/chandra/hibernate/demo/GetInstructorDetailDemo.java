package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;

public class GetInstructorDetailDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			
			// start a transaction
			session.beginTransaction();
			
			//get the instructor detail object
			int theID = 2;
			InstructorDetail tempInstructorDetail = session.get(InstructorDetail.class, theID);
			//print the instructor detail 
			
			if(tempInstructorDetail != null) {
			System.out.println(">>> Temp Isntructor Detail: " + tempInstructorDetail);
			
			//print the associated instructor
			System.out.println(">>> The associated Instructor: "+tempInstructorDetail.getInstructor());
			} else {
				System.out.println(">>> Instructor Do Not Exist with ID : " + theID);
			}
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//Handle connection leak issue
			session.close();
			
			factory.close();
		}

	}

}
