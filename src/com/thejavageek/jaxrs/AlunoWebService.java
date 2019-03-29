package com.thejavageek.jaxrs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.config.DataBase;
import com.config.FileUpload;
import com.config.JWT;
import com.entidades.Aluno;
import com.google.gson.Gson;

@Path("aluno")
public class AlunoWebService extends DataBase {

	@javax.inject.Inject
	public FileUpload fileUpload;
	
	
	@GET
	@Path("")
	@Produces(App.JSON)
	@JWT
	public Response listar() {
		List<Aluno> alunos = getCon().createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
		return Response.ok(new Gson().toJson(alunos)).build(); 
	}
	
	@POST
	@Path("upload")
	@Consumes("multipart/form-data")
	public Response upload(MultipartFormDataInput input) {
		
		System.out.println("Upload start");
		uploadFile(input);
		
		
		return Response.ok().build();
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
	
	
	
	public void uploadFile(MultipartFormDataInput input){
		String fileName = "";
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {

		 try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);

			byte [] bytes = IOUtils.toByteArray(inputStream);
				
			//constructs upload file path
			fileName = "E:\\\\Imagens\\\\" + fileName;
				
			writeFile(bytes,fileName);
				
			System.out.println("Done");

		  } catch (IOException e) {
			  System.out.println("Erro na uploadFile");
			e.printStackTrace();
		  }
		}
	}
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");
				
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
	
}
