import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			
		
			public void configure() throws Exception {
				
				
				context.start();
				//to send and recieve a message 
				from("direct:start")
				.process(new Processor() { 
					
					@Override
					public void process(Exchange exc) throws Exception {

						if(exc.getIn().getBody()!=null)
						{
							String messageBody = (String) exc.getIn().getBody();
							//Just appending som test to process original message in exchange 
							exc.getIn().setBody(exc.getIn().getBody() + " Welcome");
						}
					}
				})
				.to("seda:end");
				
				
				//******Below is the simple use of transform If not used process method****
				// from("direct:start").transform(body().prepend("Welcome: ")).to("seda:end");
			}
		
		});
			
			ProducerTemplate pt = context.createProducerTemplate();
			pt.sendBody("direct:start", "helll00123");
			System.out.println("\n\nsent message as >> helll00123" );
			
			ConsumerTemplate ct = context.createConsumerTemplate();
			String recvdString = ct.receiveBody("seda:end",  String.class);
			
			System.out.println("\nrecieved message as >> "+ recvdString);
			
	}
}
