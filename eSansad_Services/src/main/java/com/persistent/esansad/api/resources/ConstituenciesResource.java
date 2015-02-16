package com.persistent.esansad.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.persistent.esansad.dao.util.MongoDBHelper;
import com.sun.jersey.core.util.Base64;

@Path("/states/{stateName}/constituencies")
public class ConstituenciesResource {

	@GET
	@Path("/{constituencyName}/mp")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMPDetails(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName , @HeaderParam("token") String token) {
		ResponseBuilder builder = null;
		
		MongoDBHelper helper = new MongoDBHelper();
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		
		String mpDetails = helper.getMPDetailsForConstituency(constituencyName);
		JSONObject mpJson = new JSONObject(mpDetails);
		JSONObject mpJsonToBeReturned = new JSONObject();
		mpJsonToBeReturned.put("first_name", mpJson.get("first_name"))
						.put("last_name", mpJson.get("last_name"))
						.put("state", stateName)
						.put("party", mpJson.get("party"))
						.put("email", mpJson.get("email"))
						.put("constituency", mpJson.get("constituency"))
						.put("education_details", mpJson.get("education_details"))
						.put("expenditure_incurred", mpJson.get("Expenditure_incurred"))
						.put("amount_available_with_interest", mpJson.get("Amount_available_with_interest"))
						.put("unspent_balance", mpJson.get("Unspent_balance"));
		
		
		builder = Response.ok(mpJsonToBeReturned.toString());
		return builder.build();
	}
	
	@GET
	@Path("/{constituencyName}/mp/balance")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMPBalanceByCategory(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName , @HeaderParam("token") String token) {
		ResponseBuilder builder = null;
		
		MongoDBHelper helper = new MongoDBHelper();
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		
		JSONObject balance = helper.getAmoutSpentByCategory(constituencyName);
		
		builder = Response.ok(balance.toString());
		return builder.build();
	}
	
	@GET
	@Path("/{constituencyName}/projects")
	public Response getAllProjects(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName , @HeaderParam("token") String token,
			@QueryParam("from") String from,
			@QueryParam("category") String category) {
		ResponseBuilder builder = null;
		MongoDBHelper helper = new MongoDBHelper();
		String userJsonString = null;
		String email = null;
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		else if(from != null && from.equals("self")){
			userJsonString = helper.getUserDetailsFromToken(token);
			System.out.println(userJsonString);
			JSONObject userJson = new JSONObject(userJsonString);
			email = (String) userJson.get("email");
		}
		else if(from != null && from.equals("mp")) {
			String mpJsonString = helper.getMPDetailsForConstituency(constituencyName);
			JSONObject mpJson = new JSONObject(mpJsonString);
			email = (String) mpJson.get("email");
		}
		
		List<String> projectDetails = helper.getAllProjects(constituencyName, email, category);
		
		List<JSONObject> jsonResponseList = new ArrayList<JSONObject>();
		
		for(String json:projectDetails){
			JSONObject obj = new JSONObject(json);
			
			JSONObject newObj = new JSONObject();
			newObj.put("category", obj.get("category"))
			.put("submitted_by", obj.get("submitted_by"))
			.put("submitted_by_email", obj.get("submitted_by_email"))
			.put("description", obj.get("description"))
			.put("title", obj.get("title"))
			.put("upvotes", obj.get("upvotes"))
			.put("downvotes", obj.get("downvotes"))
			.put("status", obj.get("status"))
			.put("constituency", obj.get("constituency"))
			.put("cost", obj.get("cost"))
			.put("submission_date", obj.get("submission_date"))
			.put("proposal_id", obj.get("proposal_id"));
			jsonResponseList.add(newObj);
		}
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("projects", jsonResponseList);
		
		builder = Response.ok(jsonObject.toString());
		return builder.build();
	}
	
	
	
	@POST
	@Path("/{constituencyName}/proposals/{proposalId}/vote")
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response voteProposal(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName, @PathParam("proposalId") String proposalId,@FormParam("type") String vote, @HeaderParam("token") String token) {
		ResponseBuilder builder = null;
		
		MongoDBHelper helper = new MongoDBHelper();
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		
		String proposalData = null;
		
		System.out.println("Vote: "+vote);
		
		if(!helper.isReadonlyUser(token)){
			try{
				proposalData = helper.vote(proposalId, vote);
				JSONObject obj = new JSONObject(proposalData);
				
				JSONObject objResponse = new JSONObject();
				
				objResponse.put("upvotes", obj.get("upvotes"))
				.put("downvotes", obj.get("downvotes"));
				
				System.out.println("returning: " + objResponse.toString());
				builder = Response.ok(objResponse.toString());
			}catch(Exception e){
				builder = Response.status(Status.BAD_REQUEST);
			}
		}else{
			builder = Response.status(Status.UNAUTHORIZED);
		}
		
		return builder.build();
	}
	
	@GET
	@Path("/{constituencyName}/proposals")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProposals(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName, @HeaderParam("token") String token,
			@QueryParam("from") String from,
			@QueryParam("category") String category) {
		
		ResponseBuilder builder = null;
		MongoDBHelper helper = new MongoDBHelper();
		String userJsonString = null;
		String email = null;
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		else if(from != null && from.equals("self")){
			userJsonString = helper.getUserDetailsFromToken(token);
			System.out.println(userJsonString);
			JSONObject userJson = new JSONObject(userJsonString);
			email = (String) userJson.get("email");
		}
		else if(from != null && from.equals("mp")) {
			String mpJsonString = helper.getMPDetailsForConstituency(constituencyName);
			JSONObject mpJson = new JSONObject(mpJsonString);
			email = (String) mpJson.get("email");
		}
		
		List<String> proposals = helper.getProposalsForConstituency(constituencyName, email, category);
		System.out.println("query param: " + from);
		List<JSONObject> jsonResponseList = new ArrayList<JSONObject>();
		
		for(String json : proposals){
			JSONObject obj = new JSONObject(json);
			
			JSONObject newObj = new JSONObject();
			newObj.put("category", obj.get("category"))
			.put("submitted_by", obj.get("submitted_by"))
			.put("submitted_by_email", obj.get("submitted_by_email"))
			.put("description", obj.get("description"))
			.put("title", obj.get("title"))
			.put("upvotes", obj.get("upvotes"))
			.put("downvotes", obj.get("downvotes"))
			.put("status", obj.get("status"))
			.put("cost", obj.get("cost"))
			.put("constituency", obj.get("constituency"))
			.put("submission_date", obj.get("submission_date"))
			.put("proposal_id", obj.get("proposal_id"));
			jsonResponseList.add(newObj);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("proposals", jsonResponseList);
		
		builder = Response.ok(jsonObject.toString());
		return builder.build();
	}
	
	@POST
	@Path("/{constituencyName}/proposals")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response submitProposal(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName, @HeaderParam("token") String token,
			@FormParam("category") String category,
			@FormParam("submitted_by") String submitted_by,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("status") String status,
			@FormParam("submission_date") String submission_date,
			@FormParam("submitted_by_email") String submitted_by_email,
			@FormParam("cost") double cost) {
		
		ResponseBuilder builder = null;
		MongoDBHelper helper = new MongoDBHelper();
		String proposal_id = UUID.randomUUID().toString();
		
		JSONObject proposalJson = new JSONObject();
		proposalJson.put("proposal_id", proposal_id)
					.put("stateName", stateName)
					.put("category", category)
					.put("submitted_by", submitted_by)
					.put("title", title)
					.put("description", description)
					.put("status", status)
					.put("constituency", constituencyName)
					.put("submission_date", submission_date)
					.put("submitted_by_email", submitted_by_email)
					.put("cost", cost);
		
		String proposalJsonResponse = helper.createProposal(proposalJson);
		
		builder = Response.ok(proposalJsonResponse);
		return builder.build();
	}
	
	@GET
	@Path("/{constituencyName}/proposals/{proposalId}/vote")
	public Response getProposalVotes(@PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName, @PathParam("proposalId") String proposalId, @HeaderParam("token") String token) {
		ResponseBuilder builder = null;
		
		MongoDBHelper helper = new MongoDBHelper();
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
		
		String proposalData = null;
		
		proposalData = helper.getProposalVotes(proposalId);
		
		try{
			
			JSONObject obj = new JSONObject(proposalData);
			
			JSONObject objResponse = new JSONObject();
			
			objResponse.put("upvotes", obj.get("upvotes"))
			.put("downvotes", obj.get("downvotes"));
			
			builder = Response.ok(objResponse.toString());
		}catch(Exception e){
			builder = Response.status(Status.BAD_REQUEST);
		}
		
		return builder.build();
	}
	
	@GET
	@Path("/{constituencyName}/mp/image")
	public Response getMPImage(@HeaderParam("token") String token, @PathParam("constituencyName") String constituencyName,
			@PathParam("stateName") String stateName) {
		
		System.out.println("user token - " + token);
		MongoDBHelper mongoDBHelper = new MongoDBHelper();
		// authenticate the user by token.
		if(!mongoDBHelper.isAuthenticated(token)){
			// Dirty fix ;)
			//throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		
		// Get MP details  
		String mpDetails = mongoDBHelper.getMPDetailsForConstituency(constituencyName);
		JSONObject mpJson = new JSONObject(mpDetails);
		
		// fetch image from the mp details 
		String encodedImage = mpJson.getString("image");
		if(encodedImage == null || encodedImage.isEmpty()) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}
		
		byte[] decodedImage = Base64.decode(encodedImage);
				
		return Response.ok(decodedImage, "image/jpeg").build();
	}

}
