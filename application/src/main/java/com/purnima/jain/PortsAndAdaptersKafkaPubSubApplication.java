package com.purnima.jain;

import com.purnima.jain.service.BusinessDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortsAndAdaptersKafkaPubSubApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(PortsAndAdaptersKafkaPubSubApplication.class);
	
	private final BusinessDomainService businessDomainService;

	public PortsAndAdaptersKafkaPubSubApplication(BusinessDomainService businessDomainService) {
		this.businessDomainService = businessDomainService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PortsAndAdaptersKafkaPubSubApplication.class, args);
		logger.info("PortsAndAdaptersKafkaPubSubApplication started.......");
	}
	
	@Override
	public void run(String... args) throws Exception {
		businessDomainService.generateAndSendMessage();
	}

}
