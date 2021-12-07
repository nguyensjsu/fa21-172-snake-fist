package com.example.springappleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.springappleapi.messaging.Receiver;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAppleApiApplication {
   @Value("${spring.rabbitmq.exchange}")
	private String exchange;	
   @Value("${spring.rabbitmq.queue}")
	private String queue;	
   @Value("${spring.rabbitmq.routingkey}")
	private String routingkey;	

   @Bean
   Queue queue() {
      return new Queue(queue, false);
   }

   @Bean
   TopicExchange exchange() {
      return new TopicExchange(exchange);
   }

   @Bean
   Binding binding(Queue queue, TopicExchange exchange) {
      return BindingBuilder.bind(queue).to(exchange).with(routingkey);
   }

   @Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

//    @Bean
//   MessageListenerAdapter listenerAdapter(Receiver receiver) {
//     return new MessageListenerAdapter(receiver, "receiveMessage");
//   }
   
//   @Bean
//   SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//       MessageListenerAdapter listenerAdapter) {
//       SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//       container.setConnectionFactory(connectionFactory);
//       container.setQueueNames(queue);
//       container.setMessageListener(listenerAdapter);
//       return container;
//   }

   @Bean
   public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurerAdapter() {
         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("*");

         }
      };
   }

   public static void main(String[] args) {
      SpringApplication.run(SpringAppleApiApplication.class, args);
   }
}
