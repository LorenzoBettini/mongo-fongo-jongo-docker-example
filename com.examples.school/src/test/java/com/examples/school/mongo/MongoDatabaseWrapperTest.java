package com.examples.school.mongo;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.examples.school.Student;
import com.examples.school.helper.MongoTestHelper;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapperTest {

	// SUT
	private MongoDatabaseWrapper mongoDatabase;

	// helper for testing with Mongo
	private MongoTestHelper mongoTestHelper;

	@Before
	public void initDB() throws UnknownHostException {
		// in-memory java implementation of MongoDB
		// so that we don't need to install MongoDB in our computer
		Fongo fongo = new Fongo("mongo server 1");
		MongoClient mongoClient = fongo.getMongo();
		mongoTestHelper = new MongoTestHelper(mongoClient);

		mongoDatabase = new MongoDatabaseWrapper(mongoClient);
	}

	@Test
	public void testGetAllStudentsEmpty() {
		assertTrue(mongoDatabase.getAllStudentsList().isEmpty());
	}

	@Test
	public void testGetAllStudentsNotEmpty() {
		mongoTestHelper.addStudent("1", "first");
		mongoTestHelper.addStudent("2", "second");

		assertEquals(2, mongoDatabase.getAllStudentsList().size());
	}

	@Test
	public void testFindStudentByIdNotFound() {
		mongoTestHelper.addStudent("1", "first");

		assertNull(mongoDatabase.findStudentById("2"));
	}

	@Test
	public void testFindStudentByIdFound() {
		mongoTestHelper.addStudent("1", "first");
		mongoTestHelper.addStudent("2", "second");

		Student findStudentById = mongoDatabase.findStudentById("2");
		assertNotNull(findStudentById);
		assertEquals("2", findStudentById.getId());
		assertEquals("second", findStudentById.getName());
	}

}
