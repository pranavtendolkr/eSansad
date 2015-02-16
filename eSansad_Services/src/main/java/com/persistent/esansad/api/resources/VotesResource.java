package com.persistent.esansad.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import com.persistent.esansad.dao.util.MongoDBHelper;

@Path("/vote")
public class VotesResource {

	@GET
	@Path("/{proposalId}")
	public Response vote(@PathParam("proposalId") String proposalId,
			@QueryParam("type") String vote) {
		ResponseBuilder builder = null;

		MongoDBHelper helper = new MongoDBHelper();

		String proposalData = null;

		System.out.println("Vote: " + vote);
		proposalData = helper.vote(proposalId, vote);
		JSONObject obj = new JSONObject(proposalData);

		JSONObject objResponse = new JSONObject();

		objResponse.put("upvotes", obj.get("upvotes")).put("downvotes",
				obj.get("downvotes"));

		System.out.println("returning: " + objResponse.toString());
		builder = Response.ok(objResponse.toString());

		return builder.build();
	}
}
