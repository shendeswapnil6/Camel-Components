import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelCSV {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				from("direct:start")
			    .marshal().csv()
			    .to("seda:end");
				
				context.start();
			}
		});
		
		Map<String, Object> body = new HashMap<>();
		body.put("abc", "welcome");
		body.put("def", 9090);
		body.put("ghi", 7890);
		ProducerTemplate pt = context.createProducerTemplate();
		pt.sendBody("direct:start", body);
		

		ConsumerTemplate ct = context.createConsumerTemplate();
		String recvdString = ct.receiveBody("seda:end",  String.class);
		System.out.println("\nrecieved message as >> "+ recvdString);
		
	}

}
