package br.com.caelum.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Boot {

	@Autowired
	private CamelContext context;

	@PostConstruct
	public void init() throws Exception {
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
	}

	@Bean
	public RoutesBuilder rota() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:pedidos").
					to("activemq:queue:pedidos");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}
}
