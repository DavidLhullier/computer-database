package core.coreconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "core",

		"persistence.config",
		
		"service"})

public class RootConfiguration {

	


}
