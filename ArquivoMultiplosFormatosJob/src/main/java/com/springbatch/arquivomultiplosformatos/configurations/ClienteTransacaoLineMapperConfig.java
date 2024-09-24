package com.springbatch.arquivomultiplosformatos.configurations;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClienteTransacaoLineMapperConfig {

    @Bean
    public PatternMatchingCompositeLineMapper lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper<>();
        lineMapper.setTokenizers(getStringLineTokenizerMap());
        lineMapper.setFieldSetMappers(getStringFieldSetMapperMap());
        return lineMapper;
    }

    private  Map<String, FieldSetMapper> getStringFieldSetMapperMap() {
        Map<String, FieldSetMapper> fieldSetMapperMap = new HashMap<>();

        fieldSetMapperMap.put("0*", fieldSetMapper(Cliente.class));
        fieldSetMapperMap.put("1*", fieldSetMapper(Transacao.class));

        return fieldSetMapperMap;
    }

    private FieldSetMapper fieldSetMapper(Class class1)
    {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(class1);

        return fieldSetMapper;
    }
    private  Map<String, LineTokenizer> getStringLineTokenizerMap() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put("0*", clienteLineTokenizer());
        tokenizers.put("1*", transacaoLineTokenizer());

        return tokenizers;
    }

    private  LineTokenizer clienteLineTokenizer(){
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames("nome", "sobrenome", "idade", "email");
        lineTokenizer.setIncludedFields(1, 2, 3, 4);

        return lineTokenizer;
    }

    private LineTokenizer transacaoLineTokenizer(){
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames("id", "descricao", "valor");
        lineTokenizer.setIncludedFields(1, 2, 3);

        return lineTokenizer;
    }
}
