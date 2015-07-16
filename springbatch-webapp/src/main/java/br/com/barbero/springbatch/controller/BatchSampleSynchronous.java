package br.com.barbero.springbatch.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("importsynchronous")
public class BatchSampleSynchronous {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("importFile")
	private Job jobImportFile;
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(required=true, value="file") MultipartFile fileUpload) throws FileNotFoundException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		File file = createTempFile(fileUpload);
		Map<String, String> parametros =  new HashMap<String, String>();
		parametros.put("xmlEntrada", file.getAbsolutePath());
		JobExecution execution = jobLauncher.run(jobImportFile, getNext(parametros));
		return execution.getStatus().toString();
	}

	
	/***
	 * Metodo privado responsavel para criar o arquivo temporario
	 * @param fileUpload {@link MultipartFile} contendo o arquivo de upload
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	private File createTempFile(MultipartFile fileUpload) throws FileNotFoundException, IOException {
		File tempFile = File.createTempFile("student", ".xml");
		FileOutputStream fileTemp = new FileOutputStream(tempFile);
		fileTemp.write(fileUpload.getBytes());
		fileTemp.flush();
		fileTemp.close();
		
		return tempFile;
	}

	private JobParameters getNext(Map<String, String> parametros) {
		JobParameters parameters =  new JobParameters();
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		
		for (String key : parametros.keySet()) {
			jobParametersBuilder.addString(key, parametros.get(key));
		}
		
		parameters = jobParametersBuilder.addLong("currentTime", new Long(System.currentTimeMillis())).toJobParameters();
		return parameters;
	}

}
