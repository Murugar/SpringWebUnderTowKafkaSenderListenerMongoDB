package com.iqmsoft.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Component;

@Component
public class Listener {

	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

	@KafkaListener(topics = "test")
	public void receiveMessage(String message) {
		LOGGER.info("received message='{}'", message);
	}

	@EventListener()
	public void eventHandler(ListenerContainerIdleEvent event) {
		LOGGER.debug("event='{}'", event.toString());
	}

}

