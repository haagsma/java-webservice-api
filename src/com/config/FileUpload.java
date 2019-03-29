package com.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public class FileUpload {


	private final String UPLOADED_FILE_PATH = "E:\\\\Imagens\\\\";
	
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
