package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;
import com.chandra.hibernate.demo.entity.Review;
import com.chandra.hibernate.demo.entity.Student;

public class GetCoursesForStudentDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create a session

		Session session = factory.getCurrentSession();

		try {
			

			// start a transaction
			session.beginTransaction();
			
			//get the student from DB
			int studentID = 1;
			Student tempStudent = session.get(Student.class, studentID);
			
			System.out.println("\nLoaded Student: " + tempStudent);
			System.out.println("Courses: " + tempStudent.getCourses());
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} finally {
			session.close();
			
			factory.close();
		}

	}

}
