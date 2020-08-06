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
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobSimpleReadProcessWriter {

    private static final int CHUNK_SIZE = 1000;

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
        return this.stepBuilderFactory.get("step2").<Reader, Writer>chunk(CHUNK_SIZE)
            .reader(reader())
            .processor(processor()).writer(customItemWriter()).build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Reader> reader() {
        return new JpaPagingItemReaderBuilder<Reader>().name("reader")
            .queryString("select r From Reader r where r.synchronizeType = 'N'")
            .pageSize(CHUNK_SIZE).entityManagerFactory(entityManagerFactory).build();
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
