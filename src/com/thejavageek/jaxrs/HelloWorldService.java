package com.thejavageek.jaxrs;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.config.DataBase;
import com.entidades.Aluno;
import com.google.gson.Gson;

@Path("/HelloWorld")
public class HelloWorldService extends DataBase {

	
	@GET
	@Path("")
	public String sayHello() {
		return "<h1>Hello World</h1>";
	}
	
	@GET
	@Path("/nome")
	@Produces("application/json")
	public Response nome() {
		
		java.util.List<Aluno> alunos = getCon().createNativeQuery("SELECT * FROM aluno", Aluno.class).getResultList();
		return Response.ok(new Gson().toJson(alunos)).build();
	}
}
