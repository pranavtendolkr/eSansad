package com.persistent.esansad.dao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.persistent.esansad.api.jaxb.RegisterUserRequest;
import com.persistent.esansad.dao.client.MongoDBClient;
import com.persistent.esansad.models.User;

public class MongoDBHelper {
	/** The Constant LOG. */
	// private static final Log LOG = LogFactory.getLog(MongoDBHelper.class);

	private MongoDBClient mongoClient = null;

	/**
	 * Load mp collection.
	 */
	public void loadMPCollection() {
		try {
			Runtime runtime = Runtime.getRuntime();

			String mongoImportCommand = "mongoimport --db "
					+ MongoDBConstants.DB_NAME + " --collection "
					+ MongoDBConstants.MP_COLLECTION + " --type json --file "
					+ MongoDBConstants.MP_JSON_FILEPATH;

			System.out
					.println("MongoDBHelper | Importing MP data data into mongodb --> "
							+ mongoImportCommand);
			runtime.exec(mongoImportCommand);
			System.out.println("MongoDBHelper | Complete!");
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception importing into the MP Collection: "
							+ ex);
		}
	}
	
	public String createProposal(JSONObject proposalJson) {
		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalsCollection = mongoClient.getProposalsCollection();

			DBObject proposalObJ = new BasicDBObject("proposal_id", proposalJson.get("proposal_id"))
			.append("stateName", proposalJson.get("stateName"))
			.append("category", proposalJson.get("category"))
			.append("submitted_by", proposalJson.get("submitted_by"))
			.append("title", proposalJson.get("title"))
			.append("status", proposalJson.get("status"))
			.append("submission_date", proposalJson.get("submission_date"))
			.append("submitted_by_email", proposalJson.get("submitted_by_email"))
			.append("cost", proposalJson.get("cost"))
			.append("constituency", proposalJson.get("constituency"))
			.append("description", proposalJson.get("description"))
			.append("upvotes", 0)
			.append("downvotes", 0);

			System.out.println("MongoDBHelper | Proposal Details --> " + proposalObJ);
			proposalsCollection.insert(proposalObJ);
			
			return proposalObJ.toString();
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception creating proposal: "
							+ ex);
			return "{}";
		}
	}

	public String getMPDetailsForConstituency(String constituencyName) {
		String mpDetailsJSON = "{}";
		try {
			mongoClient = new MongoDBClient();
			DBCollection mpCollection = mongoClient.getMPCollection();
			DBObject mpDetails = mpCollection.findOne(new BasicDBObject(
					"constituency", constituencyName));
			mpDetailsJSON = mpDetails.toString();

			return mpDetailsJSON;
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			return mpDetailsJSON;
		}
	}

	public List<String> getProposalsForConstituency(String constituencyName, String userEmail, String category) {
		List<String> proposalsList = new ArrayList<String>();

		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalsCollection = mongoClient
					.getProposalsCollection();
			DBCursor proposals= null;
			BasicDBObject queryObj = null;
			
			if(userEmail != null) {
				queryObj = new BasicDBObject("constituency", constituencyName)
				.append("status", "proposed")
				.append("submitted_by_email", userEmail);
			}
			else {
				queryObj= new BasicDBObject("constituency", constituencyName)
				.append("status", "proposed");
			}
			
			if(category != null) {
				queryObj.append("category", category);
			}
			
			proposals = proposalsCollection.find(queryObj).sort(new BasicDBObject("upvotes", -1));
			
			while (proposals.hasNext()) {
				DBObject proposal = proposals.next();
				proposalsList.add(proposal.toString());
			}

			return proposalsList;
		} catch (Exception ex) {
			System.out.println("MongoDBHelper | Exception fetching Proposals: "
					+ ex);
			return proposalsList;
		}
	}

	public void initializeMPDataInUserCollection() {

		try {
			File file = new File(MongoDBConstants.MP_JSON_FILEPATH);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String mpJsonString = null;
			List<DBObject> mpList = new ArrayList<DBObject>();

			while ((mpJsonString = reader.readLine()) != null) {
				JSONObject mpJsonObj = new JSONObject(mpJsonString);

				DBObject mpDBObj = new BasicDBObject("first_name",
						mpJsonObj.get("first_name"))
						.append("last_name", mpJsonObj.get("first_name"))
						.append("email", mpJsonObj.get("email"))
						.append("isMP", true)
						.append("password", MongoDBConstants.MP_LOGIN_PASS)
						.append("constituency", mpJsonObj.get("constituency"));
				mpList.add(mpDBObj);
			}

			reader.close();
			System.out.println(mpList.size());
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception creating user data for MPs: "
							+ ex);
		}

	}

	public String insertUserDetails(RegisterUserRequest user) {

		try {
			mongoClient = new MongoDBClient();
			DBCollection usersCollection = mongoClient.getUsersCollection();

			String apiKey = UUID.randomUUID().toString();
			
			if(usersCollection.findOne(new BasicDBObject("email", user.getUserEmail())) != null) {
				System.out.println("User with email: " + user.getUserEmail() + " already exists!");
				return "EXISTS";
			}
			
			DBObject userObj = new BasicDBObject("email", user.getUserEmail())
					.append("password", user.getUserPassword())
					.append("apiKey", apiKey).append("isMP", false)
					.append("isReadOnly", true);

			System.out.println("MongoDBHelper | User Details --> " + userObj);

			usersCollection.insert(userObj);

			System.out.println("MongoDBHelper | Inserted --> " + userObj);

			return userObj.toString();
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			return "{} ";
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		MongoDBHelper helper = new MongoDBHelper();
		helper.getMPDetailsForConstituency("North Goa");

		helper.initializeMPDataInUserCollection();
	}

	public String updateAPIKey(User user) throws UnknownHostException {
		mongoClient = new MongoDBClient();
		DBCollection mpCollection = mongoClient.getUsersCollection();

		DBObject obj = mpCollection.findOne(new BasicDBObject("email", user
				.getUserName()));

		obj.put("apiKey", user.getToken());

		BasicDBObject document = new BasicDBObject();
		document.put("apiKey", user.getToken());

		BasicDBObject searchQuery = new BasicDBObject().append("email",
				user.getUserName());

		mpCollection.update(searchQuery, obj);
		
		return obj.toString();
		

	}

	public String findUser(User user) {
		String mpDetailsJSON = null;
		try {
			mongoClient = new MongoDBClient();
			DBCollection userCollection = mongoClient.getUsersCollection();
			DBObject mpDetails = userCollection.findOne(
					new BasicDBObject("email", user.getUserName()).append(
							"password", user.getPassword()),
					new BasicDBObject("email", 1).append("apiKey", 1)
							.append("isMP", 1).append("isReadOnly", 1));

			if (mpDetails != null) {
				mpDetailsJSON = mpDetails.toString();
			}
			System.out.println("MongoDBHelper | MP Details --> "
					+ mpDetailsJSON);
			return mpDetailsJSON;
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			return mpDetailsJSON;
		}
	}

	/**
	 * Fetch user entry from db collection.
	 *
	 * @param userEmail
	 *            - user email
	 * @return userInfo object if available.
	 * @throws UnknownHostException
	 */
	public JSONObject getUserByEmail(String userEmail)
			throws UnknownHostException {

		mongoClient = new MongoDBClient();
		DBCollection users = mongoClient.getUsersCollection();

		// fetch the record.
		DBCursor cursor = users.find(new BasicDBObject("email", userEmail
				.trim()));

		return (cursor.hasNext()) ? new JSONObject(cursor.next().toString())
				: null;

	}

	public List<String> getAllProjects(String constituencyName, String userEmail, String category) {
		List<String> projectList = new ArrayList<String>();

		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalsCollection = mongoClient
					.getProposalsCollection();
			DBCursor proposals= null;
			BasicDBObject queryObj = null;
			
			if(userEmail != null) {
				queryObj = new BasicDBObject("constituency", constituencyName)
				.append("status", "completed")
				.append("submitted_by_email", userEmail);
			}
			else {
				queryObj= new BasicDBObject("constituency", constituencyName)
				.append("status", "completed");
			}
			
			if(category != null) {
				queryObj.append("category", category);
			}
			
			proposals = proposalsCollection.find(queryObj).sort(new BasicDBObject("upvotes", -1));
			
			while (proposals.hasNext()) {
				DBObject proposal = proposals.next();
				projectList.add(proposal.toString());
			}

			return projectList;
		} catch (Exception ex) {
			System.out.println("MongoDBHelper | Exception fetching Projects: "
					+ ex);
			return projectList;
		}
	}

	public boolean isAuthenticated(String token) {

		try {
			mongoClient = new MongoDBClient();
			DBCollection userCollection = mongoClient.getUsersCollection();
			DBObject userDetails = userCollection.findOne(new BasicDBObject(
					"apiKey", token));

			if ((userDetails != null) && (token!=null)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			return false;
		}
	}
	
	public String getUserDetailsFromToken(String token) {
		try {
			mongoClient = new MongoDBClient();
			DBCollection userCollection = mongoClient.getUsersCollection();
			DBObject userDetails = userCollection.findOne(new BasicDBObject(
					"apiKey", token));

			if (userDetails != null) {
				return userDetails.toString();
			} else {
				return null;
			}

		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching User details: "
							+ ex);
			return null;
		}
	}
	
	public String vote(String proposalId, String voteType) {
		DBObject proposalData = null;
		String mpDetailsJSON = "{}";
		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalCollection = mongoClient.getProposalsCollection();
			
			DBObject userDetails = null;
			
			userDetails = proposalCollection.findOne(new BasicDBObject(
					"proposal_id", proposalId));
			
			
			DBObject obj = proposalCollection.findOne(new BasicDBObject("proposal_id", proposalId));
			
			if(voteType.equalsIgnoreCase("up")){
				Integer upvoteCountInt = (Integer) userDetails.get("upvotes");
				
				obj.put("upvotes", ++upvoteCountInt);
				
			}else if(voteType.equalsIgnoreCase("down")){
				Integer downVoteCountInt = (Integer) userDetails.get("downvotes");
				
				obj.put("downvotes", ++downVoteCountInt);
			}else{
				throw new Exception("Please specify type=up or type=down in the request body");
			}
			
			
			proposalCollection.update(userDetails, obj);
			
			proposalData = proposalCollection.findOne(new BasicDBObject(
					"proposal_id", proposalId));
			
			
			System.out.println("MongoDBHelper | MP Details --> "
					+ mpDetailsJSON);
			
			
			
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			
		}
		return proposalData.toString();
	}
	
	public String getProposalVotes(String proposalId) {
		
		String mpDetailsJSON = "{}";
		
		DBObject proposalDetails = null;
		
		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalCollection = mongoClient.getProposalsCollection();
			
			
			
			proposalDetails = proposalCollection.findOne(new BasicDBObject(
					"proposal_id", proposalId));
			
			System.out.println("MongoDBHelper | MP Details --> "
					+ mpDetailsJSON);
			
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			
		}
		return proposalDetails.toString();
	}
	
	/**
	 * Get User by token.
	 * 
	 * @param token - the apiKey
	 * @return
	 * @throws UnknownHostException
	 */
	public JSONObject getUserByToken(String token) throws UnknownHostException {
		mongoClient = new MongoDBClient();
		DBCollection users = mongoClient.getUsersCollection();

		// fetch the record.
		DBCursor cursor = users.find(new BasicDBObject("apiKey", token
				.trim()));

		return (cursor.hasNext()) ? new JSONObject(cursor.next().toString())
				: null;
	}

	/**
	 * Update the user Aadhar card info.
	 * 
	 * @param user
	 * @param aadhar
	 * @return
	 * @throws UnknownHostException
	 */
	public int updateUserAadhar(User user, JSONObject aadhar) throws UnknownHostException {
		mongoClient = new MongoDBClient();
		DBCollection userCollection = mongoClient.getUsersCollection();
		
		System.out.println("MongoDBHelper:updateUserAadhar:- Aadhar details --- " + JSON.parse(aadhar.toString()));
		
		// fetch the user to set the remaining data back.
		DBObject userObj = userCollection.findOne(new BasicDBObject("apiKey", user.getToken()));

		// adding aadhar details to user.
		userObj.put("aadhar_details", JSON.parse(aadhar.toString()));
			 
		BasicDBObject searchQuery = new BasicDBObject().append("apiKey", user.getToken());
		WriteResult response = userCollection.update(searchQuery, userObj);
		return response.getN();
	}
	
	public boolean isReadonlyUser(String token) {

		boolean readonly = false;
		
		try {
			mongoClient = new MongoDBClient();
			DBCollection userCollection = mongoClient.getUsersCollection();
			DBObject userDetails = userCollection.findOne(new BasicDBObject(
					"apiKey", token));

			readonly = (boolean) userDetails.get("isReadOnly");
			

		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			return readonly;
		}
		
		return readonly;
	}
	
	public JSONObject getAmoutSpentByCategory(String constituencyName) {
		String [] categories = new String[] {"Drinking Water Facility",
											"Education",
											"Electricity Facility",
											"Non-Conventional Energy Sources",
											"Health And Family Welfare",
											"Irrigation",
											"Roads Pathways And Bridges",
											"Sports",
											"Other Public Facilities"};
		JSONObject json = new JSONObject();
		try {
			for(String category : categories) {
				DBObject queryObj = new BasicDBObject("constituency", constituencyName)
										.append("category", category)
										.append("status", "completed");
				
				System.out.println(queryObj);
				mongoClient = new MongoDBClient();
				DBCollection mps = mongoClient.getProposalsCollection();
				
				DBCursor cursor = mps.find(queryObj);
				double cost = 0;
				
				while(cursor.hasNext()) {
					DBObject proposal = cursor.next();
					cost += (Double)proposal.get("cost");
				}
				
				json.put(category, cost);
			}
		}
		catch(Exception ex) {
			System.out
			.println("MongoDBHelper | Exception fetching MP details: "
					+ ex);
		}
		
		return json;
	}
	
	public String updateProposalImage(String proposalId, String imageURL) {
		DBObject proposalData = null;
		String mpDetailsJSON = "{}";
		try {
			mongoClient = new MongoDBClient();
			DBCollection proposalCollection = mongoClient.getProposalsCollection();
			
			DBObject userDetails = null;
			
			userDetails = proposalCollection.findOne(new BasicDBObject(
					"proposal_id", proposalId));
			
			
			DBObject obj = proposalCollection.findOne(new BasicDBObject("proposal_id", proposalId));
			
			obj.put("imageURL", imageURL);
			
			proposalCollection.update(userDetails,obj, true, false);
			
			proposalData = proposalCollection.findOne(new BasicDBObject(
					"proposal_id", proposalId));
			
			
			System.out.println("MongoDBHelper | MP Details --> "
					+ mpDetailsJSON);
			
			
			
		} catch (Exception ex) {
			System.out
					.println("MongoDBHelper | Exception fetching MP details: "
							+ ex);
			
		}
		return proposalData.toString();
	}

}
