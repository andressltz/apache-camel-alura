package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class DesafioAula5 {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:pedidos\\aula5?delay=5s&noop=true")
					.to("xslt:template-aula-5.xslt")
					.setHeader(Exchange.FILE_NAME, constant("desafio-aula-5.html"))
					.log("${body}")
					.to("file:saida");

//				ProducerTemplate producer = context.createProducerTemplate();
//				producer.sendBody("direct:entrada", "Apache Camel rocks!!!");
//
//				from("direct:entrada").
//					setHeader("data", constant("8/12/2015")).
//					to("velocity:template.vm").
//					log("${body}");
			}
		});

		context.start();
		Thread.sleep(2000);
	}
}
