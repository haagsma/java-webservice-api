package com.thejavageek.jaxrs;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.config.DataBase;
import com.entidades.Aluno;
import com.google.gson.Gson;

@Path("aluno")
public class AlunoWebService extends DataBase {

	@GET
	@Path("")
	@Produces(App.JSON)
	@Transactional
	public Response listar() {
		List<Aluno> alunos = getCon().createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
		return Response.ok(new Gson().toJson(alunos)).build();
	}
	
	@POST
	@Path("adicionar")
	@Produces(App.JSON)
	public Response add(Aluno aluno) {
		Aluno alu = new Aluno();
		alu.setNome(aluno.getNome());  
		alu.setEmail(aluno.getEmail());
		try {
			dbSave(alu);
		} catch (Exception e) {
			return Response.ok(new Gson().toJson("Erro ao salvar")).build();
		}

		return Response.ok(new Gson().toJson(alu)).build();
	}
	@POST
	@Path("editar")
	@Produces(App.JSON)
	public Response edit(Aluno aluno) {
		try {
			dbUpdate(aluno);
		} catch (Exception e) {
			return Response.ok(new Gson().toJson("Erro ao editar")).build();
		}

		return Response.ok(new Gson().toJson("sucesso")).build();
	}
	@DELETE
	@Path("{id}")
	@Produces(App.JSON)
	public Response del(@PathParam("id") Long id) {
		try {

			Aluno aluno = getEm().createQuery("SELECT a FROM Aluno a WHERE id = :id", Aluno.class)
					.setParameter("id", id)
					.getSingleResult();
			dbDelete(aluno);
		} catch (NoResultException e) {
			return Response.ok(new Gson().toJson("Esse aluno não existe, não é possível excluir")).build();
		} catch (Exception e) {
			return Response.ok(new Gson().toJson("Falhar ao excluir o aluno")).build();
		}
		return Response.ok(new Gson().toJson("Deletado com sucesso")).build();
	}
	
	
}
