package com.github.sejoung.jobs;

import com.github.sejoung.component.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobSimpleReadProcessWriter {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final TestService testService;

    @Bean
    public Job testJob() {
        return this.jobBuilderFactory.get("testJob")
            .start(step2())
            .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                log.info("step2 run");
                testService.test();
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
