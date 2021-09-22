package br.com.caelum.camel;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;


public class RotaNegociacoes {

	public static void main(String[] args) throws Exception {
		final XStream xStream = new XStream();
		xStream.alias("negociacao", Negociacao.class);

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer://negociacoes?fixedRate=true&delay=1s&period=360s")
					.to("http4://argentumws-spring.herokuapp.com/negociacoes")
					.convertBodyTo(String.class)
					.unmarshal(new XStreamDataFormat(xStream))
					.split(body())
//					.setHeader(Exchange.FILE_NAME, constant("negociacoes.xml"))
//					.to("file://saida");
					.log("${body}")
					.end();
			}
		});

		context.start();
		Thread.sleep(2000);
	}

}
