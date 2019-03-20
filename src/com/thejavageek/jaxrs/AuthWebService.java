package com.thejavageek.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;

@Path("auth")
public class AuthWebService {

	@GET
	@Path("")
	public Response auth() {
		
		 String KEY = "haagsma"; 
		 try {
			    Algorithm algorithm = Algorithm.HMAC256(KEY);
			    String token = JWT.create()
			        .withIssuer("auth0")
			        .withClaim("nome", "Jhonatan")
			        .sign(algorithm);
			    return Response.status(Response.Status.CREATED).header("bearer",token).entity(token).build();
			} catch (JWTCreationException exception){
			    //Invalid Signing configuration / Couldn't convert Claims.
				 return Response.ok(new Gson().toJson("Deu erro ao gerar o token")).build();
			}
		  
		 
		
		
	}

}
