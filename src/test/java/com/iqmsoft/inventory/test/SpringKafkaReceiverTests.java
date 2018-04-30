package com.iqmsoft.inventory.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import com.iqmsoft.kafka.producer.Receiver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaReceiverTests {

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    
    @Autowired
    private Receiver receiver;

    @Test
    public void testReceiver() throws Exception {
        // set up the Kafka producer properties
        Map<String, Object> senderProperties = KafkaTestUtils
                .senderProps(SpringKafkaAppTests.embeddedKafka
                        .getBrokersAsString());

        // create a Kafka producer factory
        ProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory<Integer, String>(
                senderProperties);

        // create a Kafka template
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(
                producerFactory);
        // set the default topic to send to
        template.setDefaultTopic(
                SpringKafkaAppTests.HELLOWORLD_RECEIVER_TOPIC);

        // get the ConcurrentMessageListenerContainers
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            if (messageListenerContainer instanceof ConcurrentMessageListenerContainer) {
                ConcurrentMessageListenerContainer<Integer, String> concurrentMessageListenerContainer = (ConcurrentMessageListenerContainer<Integer, String>) messageListenerContainer;

                // as the topic is created implicitly, the default number of
                // partitions is 1
                int partitionsPerTopic = 1;
                // wait until the container has the required number of assigned
                // partitions
                ContainerTestUtils.waitForAssignment(
                        concurrentMessageListenerContainer,
                        partitionsPerTopic);
            }
        }

    

       
        
        template.sendDefault("Hello Spring Kafka Receiver! 1");
        template.sendDefault("Hello Spring Kafka Receiver! 2");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);

       
    }
}
