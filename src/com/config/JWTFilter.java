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
import com.sun.jersey.core.util.Priority;

@Provider
@com.config.JWT
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

	String KEY = "L&64YJ.;|zzN<^kg9lLQ@2{FRZ+7<SHI|%$Y;1N,R$CP8nwi?8";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Entrou no filtro header");
		// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
 
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
 
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}

}
