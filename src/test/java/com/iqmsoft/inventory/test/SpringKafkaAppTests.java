package com.iqmsoft.inventory.test;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@SuiteClasses({ SpringKafkaSenderTests.class, SpringKafkaReceiverTests.class})
public class SpringKafkaAppTests {

	protected static final String HELLOWORLD_SENDER_TOPIC = "helloworld-sender.t";
	protected static final String HELLOWORLD_RECEIVER_TOPIC = "helloworld-receiver.t";
	
	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, HELLOWORLD_SENDER_TOPIC);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String kafkaBootstrapServers = embeddedKafka.getBrokersAsString();

		// override the property in application.properties
		System.setProperty("kafka.bootstrap.servers", kafkaBootstrapServers);
	}
}
