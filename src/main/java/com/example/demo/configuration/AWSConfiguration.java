package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Component
public class AWSConfiguration {
	
	/*
	 * @Bean
	 * 
	 * @Primary public SqsClient awsSQSClient() {
	 * 
	 * return SqsClient.builder() .region(Region.US_EAST_2) .build(); }
	 */

}
