package com.thejavageek.jaxrs;

import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("auth")
public class AuthWebService {

	@GET
	@Path("")
	public Response auth() {
		String KEY = "L&64YJ.;|zzN<^kg9lLQ@2{FRZ+7<SHI|%$Y;1N,R$CP8nwi?8";
		String jwt = Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, KEY)
				.setSubject("JWT AUTH")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+90000))
				.claim("email", "jho@jho.com")
				.claim("id", 124123)
				.compact();
		JsonObject json = Json.createObjectBuilder().add("JWT", jwt).build();
		
		return Response.status(Response.Status.CREATED).header("", json).entity(json).build();
	}
	
}
