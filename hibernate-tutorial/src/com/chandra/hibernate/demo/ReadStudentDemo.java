package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Student;

public class ReadStudentDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			// create a student object
			System.out.println("Creating new student object...");
			Student tempStudent = new Student("Rama Satya", "Sandilya", "Sandilya@sdf.com");

			// start a transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the student to Database...");
			session.save(tempStudent);

			// commit the transaction
			session.getTransaction().commit();

			//Retrieve the object from database
			System.out.println("Saved student. generated ID: " + tempStudent.getId());
			
			//get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//retrieve student based on the id:  primary key
			System.out.println("\nGetting student with ID: " + tempStudent.getId());
			
			Student myStudent = session.get(Student.class, tempStudent.getId());
			
			System.out.println("Get complete : " + myStudent.toString());
			
			//commit the transaction
			session.getTransaction().commit();
			
			
			System.out.println("Done!!");
		} finally {
			factory.close();
		}

	}

}
