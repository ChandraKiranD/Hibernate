package com.chandra.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Student;

public class QueryStudentDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			// start a transaction
			session.beginTransaction();

			//Query the students
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			
			//Display the students
			displayStudents(theStudents);

			//Query Students with Last name as 'Kiran' 
			theStudents = session.createQuery("from Student s where s.lastName='Kiran'").getResultList();
			
			//Display students with lastname as Kiran
			System.out.println("Display students whose last name is 'Kiran' \n");
			displayStudents(theStudents);
			
			//Query students with last name : Kiran or firstName : Sarvani
			theStudents = session.createQuery("from Student s where "
					+ "s.lastName='Kiran' OR s.firstName='Sarvani'").getResultList();
			
			//Display students with last name : Kiran or firstName : Sarvani
			System.out.println("Students with lastName as 'Kiran' or firstName as 'Sarvani'\n");
			displayStudents(theStudents);
			
			//Query students with email ID having dxc.com
			theStudents = session.createQuery("from Student s where s.email like '%dxc.com'").getResultList();

			//Display students with email: dxc.com
			System.out.println("Students with email ID dxc.com:\n");
			displayStudents(theStudents);
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} finally {
			factory.close();
		}

	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student tempStudent: theStudents) {
			System.out.println(tempStudent);
		}
	}

}
