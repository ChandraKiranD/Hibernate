package com.chandra.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chandra.hibernate.demo.entity.Course;
import com.chandra.hibernate.demo.entity.Instructor;
import com.chandra.hibernate.demo.entity.InstructorDetail;

public class EagerLazyDemo {

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
			
			//Get courses for the instructor
			System.out.println("::: Courses: " + tempInstructor.getCourses() + ":::");
			
			// commit the transaction
			session.getTransaction().commit();

			System.out.println("::: Done!! :::");
		} finally {
			session.close();
			
			factory.close();
		}

		
		/* Notes
		 * In case of Eager Fetch Below is displayed in Console:
		 * 
		 * Hibernate: select instructor0_.id as id1_1_0_, instructor0_.email as
		 * email2_1_0_, instructor0_.first_name as first_na3_1_0_,
		 * instructor0_.instructor_detail_id as instruct5_1_0_, instructor0_.last_name
		 * as last_nam4_1_0_, courses1_.instructor_id as instruct3_0_1_, courses1_.id as
		 * id1_0_1_, courses1_.id as id1_0_2_, courses1_.instructor_id as
		 * instruct3_0_2_, courses1_.title as title2_0_2_, instructor2_.id as id1_2_3_,
		 * instructor2_.hobby as hobby2_2_3_, instructor2_.youtube_channel as
		 * youtube_3_2_3_ from instructor instructor0_ left outer join course courses1_
		 * on instructor0_.id=courses1_.instructor_id left outer join instructor_detail
		 * instructor2_ on instructor0_.instructor_detail_id=instructor2_.id where
		 * instructor0_.id=?
		 * 
		 * ::: Instructor: Instructor [id=1, firstName=Sarvani, lastName=Ventrapragada,
		 * email=sarvani@adp.com, instructorDetail=InstructorDetail [id=1,
		 * youtubeChannel=http://www.youtube.com, hobby=Nothing]]::: ::: Courses:
		 * [Course [id=10, title=Selenium], Course [id=11, title=Testing]]::: ::: Done!!
		 * :::
		 * 
		 * 
		 * In Case of lazy fetch: 
		 * 
		 * Hibernate: select instructor0_.id as id1_1_0_,
		 * instructor0_.email as email2_1_0_, instructor0_.first_name as first_na3_1_0_,
		 * instructor0_.instructor_detail_id as instruct5_1_0_, instructor0_.last_name
		 * as last_nam4_1_0_, instructor1_.id as id1_2_1_, instructor1_.hobby as
		 * hobby2_2_1_, instructor1_.youtube_channel as youtube_3_2_1_ from instructor
		 * instructor0_ left outer join instructor_detail instructor1_ on
		 * instructor0_.instructor_detail_id=instructor1_.id where instructor0_.id=?
		 * 
		 * ::: Instructor: Instructor [id=1, firstName=Sarvani, lastName=Ventrapragada,
		 * email=sarvani@adp.com, instructorDetail=InstructorDetail [id=1,
		 * youtubeChannel=http://www.youtube.com, hobby=Nothing]]:::
		 * 
		 * Hibernate: select courses0_.instructor_id as instruct3_0_0_, courses0_.id as
		 * id1_0_0_, courses0_.id as id1_0_1_, courses0_.instructor_id as
		 * instruct3_0_1_, courses0_.title as title2_0_1_ from course courses0_ where
		 * courses0_.instructor_id=?
		 * 
		 * ::: Courses: [Course [id=10, title=Selenium], Course [id=11,
		 * title=Testing]]::: ::: Done!! :::
		 */
	}

}
