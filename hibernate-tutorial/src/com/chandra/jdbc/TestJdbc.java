package com.chandra.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {

		String url="jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false";
		String user="hbstudent";
		String pass="hbstudent";
		
		try {
			System.out.println("Connecting to database: " + url);
			Connection myCon = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Successful!");
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
