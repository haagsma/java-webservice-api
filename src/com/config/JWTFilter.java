package com.config;

import java.io.IOException;
import java.security.Key;

import javax.crypto.KeyGenerator;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.core.util.Priority;
import com.sun.jersey.spi.inject.Inject;

import io.jsonwebtoken.Jwts;

@Provider
@JWT
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

	String KEY = "L&64YJ.;|zzN<^kg9lLQ@2{FRZ+7<SHI|%$Y;1N,R$CP8nwi?8";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
 
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
 
        try {
 
            // Validate the token
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
 
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
		
	}

}
