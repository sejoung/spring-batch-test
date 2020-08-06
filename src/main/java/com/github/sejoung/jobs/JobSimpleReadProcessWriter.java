package com.github.sejoung.jobs;

import com.github.sejoung.component.entity.Reader;
import com.github.sejoung.component.entity.Writer;
import com.github.sejoung.component.service.WriterService;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobSimpleReadProcessWriter {

    private static final int CHUNKSIZE = 1000;
    
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final WriterService writerService;

    @Bean
    public Job testJob() {
        return this.jobBuilderFactory.get("testJob")
            .start(step2())
            .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2").<Reader, Writer>chunk(CHUNKSIZE)
            .reader(reader())
            .processor(processor()).writer(customItemWriter()).build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Reader> reader() {
        var reader = new JpaPagingItemReader<Reader>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select r From Reader r where r.synchronizeType = 'N'");
        reader.setPageSize(CHUNKSIZE);
        reader.setName("reader");
        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Reader, Writer> processor() {
        return reader -> new Writer(reader.getData(), reader.getKey());
    }

    @Bean
    @StepScope
    public ItemWriter<Writer> customItemWriter() {
        return writerService::save;
    }
}
