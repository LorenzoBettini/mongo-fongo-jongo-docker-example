package com.examples.school.app;

import java.net.UnknownHostException;
import java.util.List;

import com.examples.school.Database;
import com.examples.school.SchoolController;
import com.examples.school.Student;
import com.examples.school.mongo.MongoDatabaseWrapper;
import com.mongodb.MongoClient;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
		String mongoHost = "localhost";
		if (args.length > 0)
			mongoHost = args[0];
		Database database = new MongoDatabaseWrapper(new MongoClient(mongoHost));
		SchoolController schoolController = new SchoolController(database);

		System.out.println("Adding student...");
		schoolController.addStudent(new Student("1", "John Smith"));
		System.out.println("Adding student...");
		schoolController.addStudent(new Student("2", "Paolo Rossi"));

		System.out.println("The school has the following students:");
		List<Student> students = schoolController.getAllStudents();
		students.
			stream().
			forEach(
				student -> System.out.println
					("Student: " + student.getId() + " - " + student.getName())
			);
		System.out.println("School-app terminates.");
	}
}
