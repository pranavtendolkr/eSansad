package com.persistent.esansad.dao.client;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.persistent.esansad.dao.util.MongoDBConstants;

public class MongoDBClient {

	/** The mongo client. */
	private static MongoClient mongoClient  = null;
	
	/** The db. */
	private static DB db = null;
	
	/**
	 * Instantiates a new mongo db client.
	 *
	 * @throws UnknownHostException the unknown host exception
	 */
	public MongoDBClient() throws UnknownHostException {
		mongoClient = new MongoClient(MongoDBConstants.DB_HOST, MongoDBConstants.DB_PORT);
		db = mongoClient.getDB(MongoDBConstants.DB_NAME);
	}
	
	/**
	 * Gets the mongo db object.
	 *
	 * @return the mongo db object
	 */
	public DB getMongoDBObject() {
		return db;
	}
	
	/**
	 * Gets the MP collection.
	 *
	 * @return the MP collection
	 */
	public DBCollection getMPCollection() {
		return db.getCollection(MongoDBConstants.MP_COLLECTION);
	}
	
	/**
	 * Gets the users collection.
	 *
	 * @return the users collection
	 */
	public DBCollection getUsersCollection() {
		return db.getCollection(MongoDBConstants.USERS_COLLECTION);
	}
	
	/**
	 * Gets the votes collection.
	 *
	 * @return the votes collection
	 */
	public DBCollection getVotesCollection() {
		return db.getCollection(MongoDBConstants.VOTES_COLLECTION);
	}
	
	/**
	 * Gets the proposals collection.
	 *
	 * @return the proposals collection
	 */
	public DBCollection getProposalsCollection() {
		return db.getCollection(MongoDBConstants.PROPOSALS_COLLECTION);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		MongoDBClient client = new MongoDBClient();
		DBCollection collection = client.getMPCollection();
		
		DBObject firstDoc = collection.findOne(new BasicDBObject("constituency", "North Goa"));
		System.out.println("First document: " + firstDoc);
	}
}
