package com.purnima.jain.service;

import com.purnima.jain.avro.dto.PersonDto;
import com.purnima.jain.domain.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	private final BusinessDomainService businessDomainService;

	public KafkaConsumerService(BusinessDomainService businessDomainService) {
		this.businessDomainService = businessDomainService;
	}

	@KafkaListener(topics = "${spring.kafka.topic-name}")
	public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		logger.info("Receiving message from Kafka :: personDto :: {} from partition: {}", personDto, partition);
		Person person = Person.builder()
								.firstName(personDto.getFirstName().toString())
								.lastName(personDto.getLastName().toString())
								.build();
		businessDomainService.postProcessReceivedMessage(person);
	}

}