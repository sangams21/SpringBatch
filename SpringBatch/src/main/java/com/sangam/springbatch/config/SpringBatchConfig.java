package com.sangam.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.sangam.springbatch.entity.Customer;
import com.sangam.springbatch.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

	
	private JobBuilderFactory  jobBuilderFactory;
	

	private StepBuilderFactory stepBuilderFactory;
	
	private CustomerRepository custormerRepo;
	
	
	// this is used to read the file from source
	@Bean
	public FlatFileItemReader<Customer> reader(){
		
		FlatFileItemReader<Customer> itemReader=new FlatFileItemReader<>();
		
		itemReader.setResource(new FileSystemResource("D:/SpringBatch/SpringBatch/src/main/resources/customers.csv"));
		
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		
		return itemReader;
	}


	private LineMapper<Customer> lineMapper() {
		// TODO Auto-generated method stub
		
		DefaultLineMapper<Customer> lineMapper=new DefaultLineMapper<>();
		
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id","firstName","lastName","email","gender","contactNo","country","dob");

	BeanWrapperFieldSetMapper<Customer> fieldSetmapper=new BeanWrapperFieldSetMapper<>();
	
	fieldSetmapper.setTargetType(Customer.class);
	
	lineMapper.setLineTokenizer(lineTokenizer);
	lineMapper.setFieldSetMapper(fieldSetmapper);
	
	return lineMapper;
}
	
	
	@Bean
	public CustomerProcceser processer() {
		return new CustomerProcceser();
	}
	
	
	public RepositoryItemWriter<Customer> writer(){ 
		RepositoryItemWriter<Customer> writer=new RepositoryItemWriter();
		writer.setRepository(custormerRepo);
		
		writer.setMethodName("save");
		return writer;
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("csv-step").<Customer,Customer>chunk(10)
				.reader(reader())
				.processor(processer())
				.writer(writer())
				.build();
		
	}

	@Bean
	public Job runjob() {
		return jobBuilderFactory.get("importCustomers")
				.flow(step1())
				.end().build();
		
	}
	
}
