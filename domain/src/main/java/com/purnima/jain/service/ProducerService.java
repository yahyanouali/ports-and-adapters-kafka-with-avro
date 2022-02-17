package com.purnima.jain.service;

import com.purnima.jain.domain.model.Person;

public interface ProducerService {
	
	void sendMessage(Person person);

}
