package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Student;

public class PrimaryKeyDemo {

	public static void main(String[] args) {

		// create session factory
				SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
						.buildSessionFactory();

				// create a session

				Session session = factory.getCurrentSession();

				try {
					// create 3 student objects
					System.out.println("Creating 3 student objects...");
					Student tempStudent1 = new Student("Vamsi", "Krishna", "vkrishna@kpmg.com");
					Student tempStudent2 = new Student("Srinivasa", "Sarma", "dvss@gmail.com");
					Student tempStudent3 = new Student("Jayasree", "Desaraju", "jayasree@yahoo.com");


					// start a transaction
					session.beginTransaction();

					// save the 3 student objects
					System.out.println("Saving the student to Database...");
					session.save(tempStudent1);
					session.save(tempStudent2);
					session.save(tempStudent3);
					
					// commit the transaction
					session.getTransaction().commit();

					System.out.println("Done!!");
				} finally {
					factory.close();
				}
		
		
		
		
		
	}

}
