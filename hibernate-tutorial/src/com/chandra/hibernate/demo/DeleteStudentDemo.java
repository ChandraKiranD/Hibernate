package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Student;

public class DeleteStudentDemo {

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
			
			//Delete the student
//			System.out.println("Deleting student with ID : " + studentID);
//			session.delete(myStudent);
			//commit the transaction
			
			
			//Delete student with id=2
			System.out.println("Deleting student with id = 2");
			session.createQuery("delete from Student where id = 2").executeUpdate();
			
			session.getTransaction().commit();
			
			System.out.println("Done!!");
		} finally {
			factory.close();
		}

	}

}
