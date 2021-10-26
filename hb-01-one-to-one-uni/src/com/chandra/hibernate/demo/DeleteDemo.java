package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;

public class DeleteDemo {

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
			
			//Get instructor by their primary key / id
			int theId = 1;
			
			Instructor tempInstructor = session.get(Instructor.class, theId);
			
			System.out.println("Fount Instructor: " + tempInstructor);
			
			//delete the instructor
			if(tempInstructor!= null) {
				System.out.println("Deleting : " + tempInstructor);
				
				//Note: This will also delete the associated "details" object because of Cascadetype.ALL
				session.delete(tempInstructor);
			}
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} finally {
			factory.close();
		}

	}

}
