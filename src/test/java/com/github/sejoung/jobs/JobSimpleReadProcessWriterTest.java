package com.github.sejoung.jobs;

import com.github.sejoung.TestBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBatchTest
@SpringBootTest(classes = {JobSimpleReadProcessWriter.class, TestBatchConfig.class})
@RunWith(SpringRunner.class)
public class JobSimpleReadProcessWriterTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Test
    public void 통합테스트() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(ExitStatus.COMPLETED.getExitCode(),
            jobExecution.getExitStatus().getExitCode());

    }
}