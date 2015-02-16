package com.persistent.esansad.api.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.persistent.esansad.dao.util.MongoDBHelper;
import com.sun.jersey.core.util.Base64;

@Path("/proposals")
public class FileUploadService {
	@POST
	@Path("/{proposalId}/image")
	public Response uploadFile(String image, @PathParam("proposalId") String proposalId, @HeaderParam("token") String token) throws IOException {
		ResponseBuilder builder = null;
		MongoDBHelper helper = new MongoDBHelper();
		
		if(!helper.isAuthenticated(token)){
			builder = Response.status(Status.UNAUTHORIZED);
			return builder.build();
		}
 
		byte[] bb = Base64.decode(image);
		
		System.out.println("uploadedInputStream "+image);

		// save it
		writeToFile(bb, proposalId);
		
		
		
		String ipAddress = InetAddress.getLocalHost().getHostAddress();
		
		String responseStr =helper.updateProposalImage(proposalId, "http://"+ipAddress+":8000/uploaded/"+proposalId+".jpeg");
		
		System.out.println("responseStr "+responseStr);
		
		JSONObject newObj = new JSONObject();
		JSONObject obj = new JSONObject(responseStr);
		newObj.put("category", obj .get("category"))
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
		.put("imageURL", obj.get("imageURL"));
 
		builder = Response.ok(newObj.toString());
		
		return builder.build();
 
	}
 
	// save uploaded file to new location
	private void writeToFile(byte[] arr, String proposalId) throws IOException {
 
		FileOutputStream fos = null;
		
		try{
			fos = new FileOutputStream("D://uploaded/"+proposalId+".jpeg");

	        fos.write(arr);
		}catch(Exception e){
			
		}finally{
			fos.close();
		}
	}
 
}
