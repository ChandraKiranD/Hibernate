package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Student;

public class UpdateStudentDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {

			int studentID = 1;
			
			//get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//retrieve student based on the id:  primary key
			System.out.println("\nGetting student with ID: " + studentID);
			
			Student myStudent = session.get(Student.class, studentID);
			
			System.out.println("Get complete : " + myStudent);
			
			System.out.println("Updating Student...");
			
			myStudent.setFirstName("Chandra Kiran");
			myStudent.setLastName("Desaraju");
			
			//commit the transaction
			session.getTransaction().commit();
			
			//New Code
			//get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			System.out.println("Updating email for all students...");
			
			session.createQuery("update Student set email='achyuth@hotmail.com' where firstName='Sriram'")
				.executeUpdate();
			
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done!!");
		} finally {
			factory.close();
		}

	}

}
