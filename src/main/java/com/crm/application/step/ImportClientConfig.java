package com.crm.application.step;

import com.crm.application.model.Client;
import com.crm.application.repository.dao.ClientDao;
import com.crm.application.validator.LocalExceptionHandler;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportClientConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ClientDao clientDao;
    private final LocalExceptionHandler exceptionHandler;


    @Autowired
    public ImportClientConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ClientDao clientDao, LocalExceptionHandler exceptionHandler) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.clientDao = clientDao;

        this.exceptionHandler = exceptionHandler;
    }

    @Bean
    public ClientProcessor processor() {
        return new ClientProcessor();
    }

    @Bean
    public Job importUserJob(ItemReader<Client> importReader) {
        return jobBuilderFactory.get("Client")
                .incrementer(new RunIdIncrementer())
                .flow(step1(importReader))
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<Client> importReader) {

        return stepBuilderFactory
                .get("step1")
                .<Client, Client>chunk(10)
                .reader(importReader)
                .processor(processor())
                .writer(new Writer(clientDao))
                .faultTolerant() // change if u dont want to skip faulty records
                .skip(Exception.class)
                .skipLimit(10)
                .exceptionHandler(exceptionHandler)
                .build();
    }

}
