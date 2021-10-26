package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;

public class LazyLoadigSessionCloseIssueResolved {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			

			// start a transaction
			session.beginTransaction();
			
			//Get the instructor			
			int theId=1;
			Instructor tempInstructor = session.get(Instructor.class, theId);
						
			System.out.println("::: Instructor: " + tempInstructor + ":::");
			
			//Move this line here. The session is still active so there will not be any issues.
			//Option#1: Call the get method when session is open:
			
			System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");
			
			// commit the transaction
			session.getTransaction().commit();
			
			//intentionally closing the session to get the exception when loading courses that are lazy loaded.
			//Close the session
			session.close();
			System.out.println("\n::: <The session is now closed> :::\n");
			
			System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");

			//Since the courses are lazy loaded, this will fail with below exception:
			
			/*
			 * Exception in thread "main" org.hibernate.LazyInitializationException: failed
			 * to lazily initialize a collection of role:
			 * com.chandra.hibernate.demo.entity.Instructor.courses, could not initialize
			 * proxy - no Session at
			 * org.hibernate.collection.internal.AbstractPersistentCollection.
			 * throwLazyInitializationException(AbstractPersistentCollection.java:612) at
			 * org.hibernate.collection.internal.AbstractPersistentCollection.
			 * withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) at
			 * org.hibernate.collection.internal.AbstractPersistentCollection.initialize(
			 * AbstractPersistentCollection.java:591) at
			 * org.hibernate.collection.internal.AbstractPersistentCollection.read(
			 * AbstractPersistentCollection.java:149) at
			 * org.hibernate.collection.internal.PersistentBag.toString(PersistentBag.java:
			 * 621) at java.base/java.lang.String.valueOf(String.java:3367) at
			 * java.base/java.lang.StringBuilder.append(StringBuilder.java:167) at
			 * com.chandra.hibernate.demo.LazyLoadigSessionCloseIssueDemo.main(
			 * LazyLoadigSessionCloseIssueDemo.java:49)
			 */
			//Get courses for the instructor
		//	System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");

			System.out.println("::: Done!! :::");
		} finally {
			session.close();
			
			factory.close();
		}

		
	}

}
