package com.example.springappleapi.messaging;

import com.example.springappleapi.models.Ticket;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${spring.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Ticket ticket) {
		rabbitTemplate.convertAndSend(exchange, routingkey, ticket);
		System.out.println("Ticket sent");    
	}
}