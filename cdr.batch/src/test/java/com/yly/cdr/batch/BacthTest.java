package com.yly.cdr.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类
 * @author wen_ruichao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "/cdr-batch-context.xml", "/cdr-core-context.xml", "/cdr-data-context.xml", "/cdr-dao-context.xml", "/cdr-app-context.xml","/cdr-cache-context.xml" })
public abstract class BacthTest {

	@Autowired
	private JobLauncher joblauncher;
	
	@Autowired
	private Job job;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
    public void test()  throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobExecution result = joblauncher.run(job, new JobParametersBuilder().addString("messageId", getMessageId()).toJobParameters());
		System.out.println(result.toString());
	}
	
	protected abstract String getMessageId();
}