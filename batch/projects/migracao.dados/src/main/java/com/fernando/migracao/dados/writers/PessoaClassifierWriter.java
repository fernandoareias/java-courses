package com.fernando.migracao.dados.writers;

import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PessoaClassifierWriter {

    @Bean
    public ClassifierCompositeItemWriter<Pessoa> pessoaClassifierCompositeItemWriter(
            JdbcBatchItemWriter<Pessoa> banco,
            FlatFileItemWriter<Pessoa> arquivo
    )
    {
        return new ClassifierCompositeItemWriterBuilder<Pessoa>()
                .classifier(classifier(banco, arquivo))
                .build();
    }

    private Classifier<Pessoa, ItemWriter<? super Pessoa>> classifier(JdbcBatchItemWriter<Pessoa> banco, FlatFileItemWriter<Pessoa> arquivo) {

        return new Classifier<Pessoa, ItemWriter<? super Pessoa>>() {
            @Override
            public ItemWriter<? super Pessoa> classify(Pessoa classifiable) {
                if(classifiable.isValida())
                {
                    return banco;
                }
                else {
                    return arquivo;
                }
            }
        };

    }


}
