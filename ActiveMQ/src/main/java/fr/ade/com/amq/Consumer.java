package fr.ade.com.amq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	@JmsListener(destination = "sample.queue")
	public void receiveQueue(String text) {
		System.out.println("queue 1");
		System.out.println(text);
	}
	
	@JmsListener(destination = "sample.queue")
	public void receiveQueue2(String text) {
		System.out.println("queue 2");
		System.out.println(text);
	}
	
	@JmsListener(destination = "sample.topic",containerFactory="topicJmsListenerContainerFactory")
	public void receiveTopic(byte[] text) {
		System.out.println("topic 1");
		System.out.println(new String(text));
	}
	
	@JmsListener(destination = "sample.topic",containerFactory="topicJmsListenerContainerFactory")
	public void receiveTopic2(byte[] text) {
		System.out.println("topic 2");
		System.out.println(new String(text));
	}


}

