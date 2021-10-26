package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;

public class LazyLoadigSessionCloseIssueResolvedHQL {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {

			// start a transaction
			session.beginTransaction();

			// Get the instructor
			int theId = 1;

			//Option2: Use HQL
			Query<Instructor> query = session.createQuery(
					"select i from Instructor i " + "JOIN FETCH i.courses " + "where i.id=:theInstructorID",
					Instructor.class);
			
			query.setParameter("theInstructorID", theId);
			
			//execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("::: Instructor: " + tempInstructor + ":::");

			// Move this line here. The session is still active so there will not be any
			// issues.

			// commit the transaction
			session.getTransaction().commit();

			// Intentionally closing the session to get the exception when loading courses
			// that are lazy loaded.
			// Close the session
			session.close();
			System.out.println("\n::: <The session is now closed> :::\n");

			System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");

			// Since the courses are lazy loaded, this will fail with below exception:

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
			// Get courses for the instructor
			// System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");

			System.out.println("::: Done!! :::");
		} finally {
			session.close();

			factory.close();
		}

	}

}
