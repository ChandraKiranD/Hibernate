package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;
import com.chandra.hibernate.demo.entity.Review;

public class CreateCourseAndReviewsDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			

			// start a transaction
			session.beginTransaction();
			
			//create a course
			Course tempCourse = new Course("PACMAN - Game!!");
			
			//add some reviews
			tempCourse.addReview(new Review("Great Course.."));
			tempCourse.addReview(new Review("Very useful Course.."));
			tempCourse.addReview(new Review("Helped me a lot"));
			
			//save the course and leverage the cascade all 
			System.out.println("Saving the course" + tempCourse);
			System.out.println(tempCourse.getReviews());
			session.save(tempCourse);
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} finally {
			session.close();
			
			factory.close();
		}

	}

}
