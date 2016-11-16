package com.examples.school.mongo;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.examples.school.Database;
import com.examples.school.Student;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapper implements Database {

	private DBCollection students;

	public MongoDatabaseWrapper(MongoClient mc) throws UnknownHostException {
		DB db = mc.getDB("school");

		students = db.getCollection("student");
	}

	@Override
	public List<Student> getAllStudentsList() {
		DBCursor cursor = students.find();
		return StreamSupport.
				stream(cursor.spliterator(), false).
				map(e -> new Student((String) e.get("id"), (String) e.get("name"))).
				collect(Collectors.toList());
	}

	@Override
	public Student findStudentById(String id) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", id);
		DBObject findOne = students.findOne(searchQuery);
		return findOne != null ?
				new Student((String) findOne.get("id"), (String) findOne.get("name")) :
				null;
	}

}
