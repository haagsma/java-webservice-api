package com.config;

import java.io.IOException;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;
import com.sun.jersey.core.util.Priority;

@Provider
@com.config.JWT
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

	String KEY = "haagsma";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Entrou no filtro header");
		// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
 
 
        try {
            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
		        .withClaim("nome", "Jhonatan")
                .build(); //Reusable verifier instance
            System.out.println("TOKEN: "+token);
            DecodedJWT jwt = verifier.verify(token.trim());
            System.out.println("JWT: "+token);
        } catch (JWTVerificationException exception){
        	System.out.println("Exception verifier:\n"+exception);
            requestContext.abortWith(Response.ok(new Gson().toJson("Erro JWTVerificationException no filter")).build());
        } catch (Exception e) {
        	System.out.println("Exception:\n"+e);
            requestContext.abortWith(Response.ok(new Gson().toJson("Token inválido verifique se o token está com bearer")).build());
		}
	}

}
