package br.com.barbero.springbatch.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ImportService {
	
	private Log logger = LogFactory.getLog(ImportService.class);

	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("importFileMulti")
	private Job jobImportFile;
	
	@Scheduled(fixedDelay = 10000)
	public void executeBatch() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
	    String pastaTemporaria = System.getProperty("java.io.tmpdir");
		Map<String, String> parametros =  new HashMap<String, String>();
		parametros.put("pastatemp", pastaTemporaria);
		JobExecution execution = jobLauncher.run(jobImportFile, getNext(parametros));
		
		logger.info(String.format("Executado com sucesso %s", execution.getStatus().toString()));
		
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
