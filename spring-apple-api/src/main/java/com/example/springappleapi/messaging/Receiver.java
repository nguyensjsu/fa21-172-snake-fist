// package com.example.springappleapi.messaging;

// import java.util.concurrent.CountDownLatch;

// import com.example.springappleapi.models.Ticket;

// import org.springframework.amqp.rabbit.annotation.RabbitHandler;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Component;

// // @RabbitListener(queues = "support")
// // public class Receiver {

// //     @RabbitHandler
// //     public void receive(String in) {
// //         System.out.println(" [x] Received '" + in + "'");
// //     }
    
// // }

// @Component
// public class Receiver {

//   private CountDownLatch latch = new CountDownLatch(1);

//   public void receiveMessage(Ticket ticket) {
//     System.out.println("Received <" + ticket.getMessage() + ">");
//     latch.countDown();
//   }

//   public CountDownLatch getLatch() {
//     return latch;
//   }

// }






package com.example.springappleapi.messaging;

import com.example.springappleapi.models.Ticket;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${spring.rabbitmq.routingkey}")
	private String routingkey;	
    @Value("${spring.rabbitmq.queue}")
	private String queue;	
	
	public Ticket receive() {
        System.out.println("Receive sent");    
		return (Ticket)rabbitTemplate.receiveAndConvert(queue);
	}
}