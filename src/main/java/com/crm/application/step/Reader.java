package com.crm.application.step;

import com.crm.application.model.Client;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Reader {

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FlatFileItemReader<Client> importReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) throws IOException {
        FlatFileItemReader<Client> reader = new FlatFileItemReader<>();

        reader.setLineMapper(new DefaultLineMapper<Client>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"client_type", "phone", "email", "description", "first_name", "last_name", "pesel", "name", "nip", "regon", "type", "website", "trade"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Client>() {{
                setTargetType(Client.class);
            }});
        }});

        reader.setResource(new FileSystemResource(pathToFile));

        return reader;
    }

}
