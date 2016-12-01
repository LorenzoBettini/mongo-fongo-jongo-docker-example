package com.examples.school.mongo;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.examples.school.Database;
import com.examples.school.Student;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapper implements Database {

	private MongoCollection students;

	public MongoDatabaseWrapper(MongoClient mc) throws UnknownHostException {
		DB db = mc.getDB("school");

		Jongo jongo = new Jongo(db);
		students = jongo.getCollection("student");
	}

	@Override
	public List<Student> getAllStudentsList() {
		Iterable<Student> iterable = students.find().as(Student.class);
		return StreamSupport.
			stream(iterable.spliterator(), false).
			collect(Collectors.toList());
	}

	@Override
	public Student findStudentById(String id) {
		return students.findOne("{id: #}", id).as(Student.class);
	}

	@Override
	public void save(Student student) {
		students.save(student);
	}

}