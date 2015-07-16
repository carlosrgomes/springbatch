package br.com.barbero.springbatch.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("importasync")
public class BatchSampleAsync {
	
	

	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public void upload(@RequestParam(required=true, value="file") MultipartFile fileUpload) throws FileNotFoundException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		createTempFile(fileUpload);
	}

	
	/***
	 * Metodo privado responsavel para criar o arquivo temporario
	 * @param fileUpload {@link MultipartFile} contendo o arquivo de upload
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	private File createTempFile(MultipartFile fileUpload) throws FileNotFoundException, IOException {
		File tempFile = File.createTempFile("estudante", ".xml");
		FileOutputStream fileTemp = new FileOutputStream(tempFile);
		fileTemp.write(fileUpload.getBytes());
		fileTemp.flush();
		fileTemp.close();
		
		return tempFile;
	}


}
