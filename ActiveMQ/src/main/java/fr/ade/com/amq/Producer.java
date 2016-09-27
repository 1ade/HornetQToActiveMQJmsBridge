package fr.ade.com.amq;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplateQueue;

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplateTopic;
	
	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			send("producer 2 Sample message "+i);
			Thread.sleep(1000);
		}
		
	}

	public void send(String msg) {
		//this.jmsMessagingTemplateTopic.send(MessageBuilder.withPayload(msg).build());
		this.jmsMessagingTemplateTopic.send(MessageBuilder.withPayload(msg.getBytes(StandardCharsets.UTF_8)).build());
		

		this.jmsMessagingTemplateQueue.send(MessageBuilder.withPayload(msg).build());
		
	}

}
