import javax.servlet.http.HttpServletResponse;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelHttp4 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
			
				//Below is the simple route to use jetty and provide greeting service
				//Just go to the browser and hit url http://0.0.0.0:8888/greeting
				from("jetty://http://0.0.0.0:8888/greeting").process(new Processor() {
					
					@Override
					public void process(Exchange exc) throws Exception {
						// TODO Auto-generated method stub
						exc.getIn().setBody(exc.getIn().getBody() + " Welcome");
					    HttpServletResponse res = 
					    exc.getIn().getBody(HttpServletResponse.class);
					    res.setCharacterEncoding("UTF-8");
					   
					}
				})
				.log("Received a request")  
				.setBody(simple("Hello, world!"));
				
				
			
				//*********  Below is simple code for http4 but giving an exception as shared below ******** 
		/*		from("direct:start")
				   .setHeader(Exchange.HTTP_METHOD, 
				       constant(org.apache.camel.component.http4.HttpMethods.POST))
				   .to("http4://www.google.com")
				   .to("mock:results");
			//.setHeader("CamelHttpMethod", constant("POST")) >> this could be also be done
			//Exception: Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/camel/http/common/UrlRewrite
			*/
				context.start();
			
			}
		});
		
	}

}
