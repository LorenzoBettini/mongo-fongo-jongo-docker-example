package com.examples.school.it;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.examples.school.Database;
import com.examples.school.SchoolController;
import com.examples.school.Student;
import com.examples.school.mongo.MongoDatabaseWrapper;
import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class SchoolControllerIT {
	private Database database;
	// to add elements in the students table for testing
	private DBCollection students;
	private SchoolController schoolController;

	@Before
	public void setUp() throws Exception {
		// in-memory java implementation of MongoDB
		// so that we don't need to install MongoDB in our computer
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();

		// make sure to drop the students table for testing
		DB db = mongoClient.getDB("school");
		db.getCollection("student").drop();

		database = new MongoDatabaseWrapper(mongoClient);
		students = db.getCollection("student");

		// we don't mock the database:
		// we use a real instance of MongoDatabaseWrapper
		schoolController = new SchoolController(database);
	}

	@Test
	public void testGetAllStudentsWhenThereAreNoStudents() {
		List<Student> allStudents = schoolController.getAllStudents();
		assertEquals(0, allStudents.size());
	}

	@Test
	public void testGetAllStudentsWhenThereIsOneStudent() {
		addStudent("1", "test");
		List<Student> allStudents = schoolController.getAllStudents();
		assertEquals(1, allStudents.size());	
	}

	@Test
	public void testGetStudentByIdWhenStudentIsNotThere() {
		addStudent("1", "test");
		Student student = schoolController.getStudentById("2");
		assertNull(student);
	}

	@Test
	public void testGetStudentByIdWhenStudentIsThere() {
		addStudent("1", "test");
		Student student = schoolController.getStudentById("1");
		assertNotNull(student);
		assertEquals("test", student.getName());
	}

	private void addStudent(String id, String name) {
		BasicDBObject document = new BasicDBObject();
		document.put("id", id);
		document.put("name", name);
		students.insert(document);
	}
}
