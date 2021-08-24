package webservice;

/*
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    // bean definitions
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
	    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	    servlet.setApplicationContext(applicationContext);
	    servlet.setTransformWsdlLocations(true);
	    return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("CountriesPort");
	    wsdl11Definition.setLocationUri("/ws");
	    wsdl11Definition.setTargetNamespace("http://www.baeldung.com/springsoap/gen");
	    wsdl11Definition.setSchema(countriesSchema);
	    return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("countries.xsd")); 
	}
}*/