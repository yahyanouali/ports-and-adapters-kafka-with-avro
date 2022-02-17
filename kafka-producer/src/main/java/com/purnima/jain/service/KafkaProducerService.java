package com.purnima.jain.service;

import com.purnima.jain.avro.dto.PersonDto;
import com.purnima.jain.domain.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService implements ProducerService {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
	
	@Value("${spring.kafka.topic-name}")
	private String topicName;
	
	private final KafkaTemplate<String, PersonDto> kafkaTemplate;

	KafkaProducerService(KafkaTemplate<String, PersonDto> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(Person person) {
		PersonDto personDto = PersonDto.newBuilder()
										.setFirstName(person.getFirstName())
										.setLastName(person.getLastName())
										.build();
		logger.info("Sending message to Kafka :: personDto :: {}", personDto);
		
		ListenableFuture<SendResult<String, PersonDto>> future = kafkaTemplate.send(topicName, personDto);

		future.addCallback(new ListenableFutureCallback<>() {

			@Override
			public void onSuccess(SendResult<String, PersonDto> result) {
				logger.info("Sent message=[{}] with offset=[{}]", personDto, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.info("Unable to send message=[{}] due to : ", personDto, ex);
			}
		});
	}
	
}
