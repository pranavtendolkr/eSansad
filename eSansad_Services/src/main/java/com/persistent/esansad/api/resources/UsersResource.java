package com.persistent.esansad.api.resources;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.persistent.esansad.api.jaxb.AuthenticateUserRequest;
import com.persistent.esansad.api.jaxb.RegisterUserRequest;
import com.persistent.esansad.api.jaxb.UserResponse;
import com.persistent.esansad.dao.util.MongoDBHelper;
import com.persistent.esansad.models.AadharDetail;
import com.persistent.esansad.models.User;
import com.sun.jersey.core.util.Base64;

@Path("/users")
public class UsersResource {

	@POST
	@Path("/register")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registerUser(@FormParam("username") String username,
			@FormParam("password") String password) {
		ResponseBuilder builder = null;

		byte[] encodedPassword = Base64.encode(password.getBytes());
		RegisterUserRequest request = new RegisterUserRequest();
		request.setUserEmail(username);
		request.setUserPassword(new String(encodedPassword));

		MongoDBHelper helper = new MongoDBHelper();
		String userInfo = helper.insertUserDetails(request);
		
		if(userInfo.equals("EXISTS")) {
			throw new WebApplicationException(
					Response.Status.BAD_REQUEST);
		}
		
		JSONObject insertedRecord = new JSONObject(userInfo);
		insertedRecord.remove("password");
		insertedRecord.remove("_id");

		builder = Response.ok(insertedRecord.toString()).status(
				Status.CREATED.getStatusCode());
		return builder.build();
	}

	private static final String AUTHENTICATION_SCHEME = "Basic";

	@POST
	@Path("/auth/token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(
			@HeaderParam("Authorization") String authorization) {

		String uuid = UUID.randomUUID().toString();
		ResponseBuilder builder = null;

		System.out.println("UsersResource.authenticateUser()");

		final String encodedUserPassword = authorization.replaceFirst(
				AUTHENTICATION_SCHEME + " ", "");
		// Decode data on other side, by processing encoded data
		byte[] valueDecoded = Base64.decode(encodedUserPassword.getBytes());
		System.out.println("Decoded value is " + new String(valueDecoded));

		String[] authDetails = new String(valueDecoded).split(":");

		System.out.println("Username: " + authDetails[0] + " Password: "
				+ authDetails[1]);

		MongoDBHelper mongoDBHelper = new MongoDBHelper();

		User user = new User();
		user.setToken(uuid);
		user.setUserName(authDetails[0]);
		
		String userResponse = null;

		try {
			userResponse = mongoDBHelper.updateAPIKey(user);
		} catch (UnknownHostException e) {

		}

		JSONObject jsonObject = new JSONObject(userResponse);
		
		JSONObject response = new JSONObject();
		response.put("apiKey", jsonObject.get("apiKey"))
		.put("email", jsonObject.get("email"))
		.put("isMP", jsonObject.get("isMP"))
		.put("isReadOnly", jsonObject.get("isReadOnly"));

		builder = Response.ok(response.toString());
		return builder.build();

	}

	@GET
	@Path("/auth/token")
	public Response getApiKey(@HeaderParam("Authorization") String authorization) {

		final String encodedUserPassword = authorization.replaceFirst(
				AUTHENTICATION_SCHEME + " ", "");
		// Decode data on other side, by processing encoded data
		byte[] valueDecoded = Base64.decode(encodedUserPassword.getBytes());
		System.out.println("Decoded value is " + new String(valueDecoded));

		String[] authDetails = new String(valueDecoded).split(":");

		byte[] encodedPassword = Base64.encode(authDetails[1]);

		User user = new User();
		user.setUserName(authDetails[0]);
		user.setPassword(new String(encodedPassword));

		MongoDBHelper mongoDBHelper = new MongoDBHelper();

		String response = mongoDBHelper.findUser(user);

		System.out.println("Username: " + user.getUserName() + " Password: "
				+ user.getPassword());

		JSONObject responseJSONBuilder = new JSONObject();
		JSONObject responseJSON = new JSONObject();
		
		if(response!=null){
			JSONObject jsonObject = new JSONObject(response);
			
			responseJSONBuilder.put("email", jsonObject.get("email"));
			responseJSONBuilder.put("apiKey", jsonObject.get("apiKey"));
			
			responseJSONBuilder.put("isMP", jsonObject.get("isMP"));
			responseJSONBuilder.put("isReadOnly", jsonObject.get("isReadOnly"));
			
			responseJSON.put("userDetails", responseJSONBuilder);
		}
		
		ResponseBuilder builder = null;

		System.out.println("Response: " + response);

		if (response != null) {
			builder = Response.ok(responseJSON.toString());
		} else {
			builder = Response.status(Status.UNAUTHORIZED);
		}

		return builder.build();

	}

	@POST
	@Path("/authenticate")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response authenticateUser(@FormParam("username") String username,
			@FormParam("password") String password) {

		// Basic authentication ...
		try {
			if (username != null && password != null) {

				// Encode user password for lookup
				byte[] encodedPassword = Base64.encode(password.getBytes());

				// create the request.
				AuthenticateUserRequest request = new AuthenticateUserRequest();
				request.setUserEmail(username);
				request.setPassword(new String(encodedPassword));

				// fetch the user by email
				MongoDBHelper helper = new MongoDBHelper();
				JSONObject userInfo = helper.getUserByEmail(request.getUserEmail());

				System.out.println(userInfo.toString());
				System.out.println(userInfo.getString("password").equals(request.getPassword()));

				// check for user.
				if (userInfo == null
						|| !(userInfo.getString("password").equals(request.getPassword()))) {
					// user not found
					throw new WebApplicationException(Response.Status.UNAUTHORIZED);
				}

				// mask some data
				userInfo.remove("password");
				userInfo.remove("_id");

				return Response.ok(userInfo.toString()).build();
			} else {
				// username/password is null.
				throw new WebApplicationException(Response.Status.UNAUTHORIZED);
			}
		} catch (JSONException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (UnknownHostException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@POST
	@Path("/aadhar")
	@Produces({ MediaType.APPLICATION_JSON  })
	public Response linkUserAadhar(@HeaderParam("token") String token, String aadharDetails) {

		System.out.println(token);
		MongoDBHelper mongoDBHelper = new MongoDBHelper();
		// authenticate the user by token.
		if(!mongoDBHelper.isAuthenticated(token)){
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		
		// the aadhar details...
		System.out.println(aadharDetails.toString());
		
		try {
			// parse the xml to bean.
			AadharDetail aadharDetail = xmlToBean(aadharDetails); 

		    System.out.println(aadharDetail.toString());    

			// user
			User user = new User();
			user.setToken(token);
			
			
			// update the aadhar data.
			int isUpdated = mongoDBHelper.updateUserAadhar(user, new JSONObject(aadharDetail) );
						
			if(isUpdated <= 0) {
				// not updated .. what went wrong? 
				System.out.println("ERROR!!! Record not updated");
				throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
			}
			
			// fetch updated user and return.
			JSONObject userInfo = mongoDBHelper.getUserByToken(user.getToken());
			return Response.ok(userInfo.toString()).build();

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.SERVICE_UNAVAILABLE);
		} 
		
		
	}

	protected AadharDetail xmlToBean(String aadharDetails)
			throws FactoryConfigurationError, XMLStreamException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		// parse the string and get document.
		AadharDetail aadharDetail = new AadharDetail();

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(new StringReader(aadharDetails));

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				// If we have an item element, we create a new item
				if (startElement.getName().getLocalPart() == "PrintLetterBarcodeData") {
					// We read the attributes from this tag and add the date
					// attribute to our object
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						// call the setter methods to populate the aadhar bean.
						Method method = AadharDetail.class.getDeclaredMethod(
								"set" + StringUtils.capitalize(attribute.getName().toString()),
								String.class);
						method.invoke(aadharDetail, attribute.getValue().toString());
					} // while end.
				} //

			}// startElement ends.

		}
		return aadharDetail;
	}
	

}
