package fr.ade.com.amq;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJms
@ImportResource("classpath:/spring/spring-config.xml")
public class App 
{
	@Autowired
	public ConnectionFactory jmsFactory;


	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}

	@Bean
	public Topic topic() {
		return new ActiveMQTopic("sample.topic");
	}

	@Bean
	public JmsTemplate jmsQueueTemplate(){
		JmsTemplate jmsQueueTemplate = new JmsTemplate(jmsFactory);
		jmsQueueTemplate.setPubSubDomain(false);
		jmsQueueTemplate.setDefaultDestination(this.queue());
		return jmsQueueTemplate;
	}
	
	@Bean
	public JmsTemplate jmsTopicTemplate(){
		JmsTemplate jmsTopicTemplate = new JmsTemplate(jmsFactory);
		jmsTopicTemplate.setPubSubDomain(true);
		jmsTopicTemplate.setDefaultDestination(this.topic());
		return jmsTopicTemplate;
	}

	@Bean
	public JmsMessagingTemplate jmsMessagingTemplateQueue() {
		JmsMessagingTemplate jmsMessagingTemplateQueue = new JmsMessagingTemplate(jmsQueueTemplate());
		jmsMessagingTemplateQueue.setDefaultDestination(this.queue());
		return jmsMessagingTemplateQueue;
	}

	@Bean
	public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(this.jmsFactory);
		factory.setConcurrency("1");
		factory.setPubSubDomain(true);
		return factory;
	}

	@Bean
	public JmsMessagingTemplate jmsMessagingTemplateTopic() {
		JmsMessagingTemplate jmsMessagingTemplateTopic = new JmsMessagingTemplate(jmsTopicTemplate());
		jmsMessagingTemplateTopic.setDefaultDestination(this.topic());
		return jmsMessagingTemplateTopic;
	}



	public static void main( String[] args )
	{
		SpringApplication.run(App.class, args);
	}
}
