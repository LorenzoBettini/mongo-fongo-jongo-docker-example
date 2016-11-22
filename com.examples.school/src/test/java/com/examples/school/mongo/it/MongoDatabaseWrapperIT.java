package com.examples.school.mongo.it;

import java.net.UnknownHostException;

import com.examples.school.common.AbstractMongoDatabaseWrapperTest;
import com.mongodb.MongoClient;

public class MongoDatabaseWrapperIT extends AbstractMongoDatabaseWrapperTest {

	@Override
	public MongoClient createMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient();
		return mongoClient;
	}

}
