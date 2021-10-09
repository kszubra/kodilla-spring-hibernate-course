package com.kodilla.course.springhibernatecourse.csvconverter;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //skonfigurowanie ItemReadera, którego Spring Batch użyje do odczytania pliku csv
    @Bean
    FlatFileItemReader<Product> reader() {
        //ustaw mu zasób wejściowy
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input.csv"));

        //LineTokenizer - powie w jaki sposób podzielić linię tekstu na poszczególne pola
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "quantity", "price");

        //LineMapper - "powie" Spring Batchowi, w jaki sposób należy kolejne odczytywane z pliku linie, konwertować na obiekty klasy Product
        BeanWrapperFieldSetMapper<Product> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Product.class);

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1); //pomiń pierwsza linię jako nagłówek
        return reader;
    }

    // utworzenie beana z ItemProcessorem,
    @Bean
    ProductProcessor processor() {
        return new ProductProcessor();
    }

    //ItemWriter, który zapisze zmodyfikowane obiekty do nowego pliku tekstowego
    @Bean
    FlatFileItemWriter<Product> writer() {
        String[] names = new String[] {"id", "quantity", "price"};
        BeanWrapperFieldExtractor<Product> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(names);

        DelimitedLineAggregator<Product> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);

        FlatFileItemWriter<Product> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output.csv"));
        writer.setHeaderCallback(
                new FlatFileHeaderCallback() {
                    public void writeHeader(Writer writer) throws IOException {
                        writer.write(Arrays.toString(names).replaceAll("\\[", "").replaceAll("]", ""));
                    }
                }
        );
        writer.setShouldDeleteIfExists(true);
        writer.setLineAggregator(aggregator);

        return writer;
    }

    //Czas skonfigurować job. Będzie się on składał z jednego kroku
    @Bean
    Step priceChange(
            ItemReader<Product> reader,
            ItemProcessor<Product, Product> processor,
            ItemWriter<Product> writer) {
        return stepBuilderFactory.get("priceChange")
                .<Product, Product>chunk(50)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    //Końcowym etapem jest skonfigurowanie całego procesu, czyli joba
    @Bean
    Job changePriceJob(Step priceChange) {
        return jobBuilderFactory.get("changePriceJob")
                .incrementer(new RunIdIncrementer())
                .flow(priceChange)
                .end()
                .build();
    }

}
