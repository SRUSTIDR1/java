package testMyService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

//import tips.java.api.ResponseMessage;

@Path("login")
public class ServiceProvider {
	
	
	@GET
	@Produces("application/xml")
	public String requestLogin()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<note>");
		
		sb.append("</note>");
		return sb.toString();
	}

	
	@POST
	@Path("/{p1}/{p2}")
	@Produces("application/json")
	public String login(@PathParam("p1") String username, @PathParam("p2") String password)
	{
		ResponseMessage msg = new ResponseMessage();
		
		if(username.equals("Admin") && password.equals("Admin1"))
		{
			msg.setResponseCode("200");
			msg.setResponseMsg("welcome");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMsg("Invalid Credentials");
		}
		
		
		return new Gson().toJson(msg);
	}

}