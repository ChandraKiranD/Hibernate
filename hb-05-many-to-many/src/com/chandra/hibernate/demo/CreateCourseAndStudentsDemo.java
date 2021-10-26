package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;
import com.chandra.hibernate.demo.entity.Review;
import com.chandra.hibernate.demo.entity.Student;

public class CreateCourseAndStudentsDemo {

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
			
			//create a course
			Course tempCourse = new Course("Skating - Game!!");
			
			
			//Save the course
			System.out.println("Saving the course...");
			session.save(tempCourse);
			System.out.println("Saved the course...: " + tempCourse);
			
			//create students
			
			Student student1 = new Student("Chandra","Kiran", "dck@gmail.com");
			Student student2 = new Student("Sarvani","V", "Sarvani@gmail.com");
			Student student3 = new Student("Sriram","Achyuth", "Achyuth@gmail.com");

			//add students to courses
			tempCourse.addStudent(student1);
			tempCourse.addStudent(student2);
			tempCourse.addStudent(student3);

			//save the students
			System.out.println("Saving Students...");
			session.save(student1);
			session.save(student2);
			session.save(student3);
			System.out.println("Saving students completed.." +tempCourse.getStudents());
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} finally {
			session.close();
			
			factory.close();
		}

	}

}
