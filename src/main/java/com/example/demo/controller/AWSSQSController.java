package com.example.demo.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.AWSConfiguration;



import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;

import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

@RestController
public class AWSSQSController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSSQSController.class);

	@AutoConfigureOrder
	AWSConfiguration awsConfig;

	//@Value("")
	String queueUrl = "https://sqs.us-east-1.amazonaws.com/your-aws-account-id/your-queue-name";

	String batchQueueUrl = "https://sqs.us-east-1.amazonaws.com/your-aws-account-id/your-queue-name";


	@PostMapping("/v1/api/send")
	public String sendMessagesToQueue(@RequestBody String jsonMessage) {
		int i=0;
		LOGGER.info("jsonMessage" + jsonMessage);
		final SqsClient sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
		try {
			
			
			SendMessageRequest messageRequest = SendMessageRequest.builder().queueUrl(queueUrl).messageBody(jsonMessage).delaySeconds(1).build();
			SendMessageResponse smr = null;
			LOGGER.info("iinside try after sendmessage reequest" );
			
			while(i<100) {
				
				smr = sqsClient.sendMessage(
						SendMessageRequest.builder().queueUrl(queueUrl).messageBody(jsonMessage).delaySeconds(1).build());
				LOGGER.info("Message ID" + smr.messageId());
				i++;
				
			}
			if (smr.sdkHttpResponse().statusCode() != 200) {
				throw new RuntimeException(
						String.format("got error response from SQS queue %s: %s", queueUrl, smr.sdkHttpResponse()));
			}
			LOGGER.info("Message ID" + smr.messageId());

		} catch (SqsException e) {
			LOGGER.error(e.awsErrorDetails().errorMessage());

		}catch (Exception e) {
			e.printStackTrace();

		}

		return "Successfull";

	}
	
	
	@PostMapping("/v1/api/sendtext")
	public String sendBatchMessagesToQueue() {

		LOGGER.info("Starting batch creation");
		final SqsClient sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
		try {
			
			 SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
				        .queueUrl(queueUrl)
				        .entries(SendMessageBatchRequestEntry.builder().id("id1").messageBody("Sending from msg 1").build(),
				                SendMessageBatchRequestEntry.builder().id("id2").messageBody("msg 2").delaySeconds(5).build(),
				                SendMessageBatchRequestEntry.builder().id("id3").messageBody("msg 3").delaySeconds(5).build(),
				                SendMessageBatchRequestEntry.builder().id("id4").messageBody("msg 4").delaySeconds(5).build(),
				                SendMessageBatchRequestEntry.builder().id("id5").messageBody("msg 5").delaySeconds(5).build())
				        .build();
			
			
			
			
		
		SendMessageBatchResponse messageResponse = 	 sqsClient.sendMessageBatch(sendMessageBatchRequest);

			if (messageResponse.sdkHttpResponse().statusCode() != 200) {
				throw new RuntimeException(
						String.format("got error response from SQS queue %s: %s", queueUrl, messageResponse.sdkHttpResponse()));
			}
			LOGGER.info("Request ID" + messageResponse.responseMetadata().requestId());

		} catch (SqsException e) {
			LOGGER.error(e.awsErrorDetails().errorMessage());

		}

		return "Successfull";

	}

}
