package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration

@ComponentScan(basePackages = { "persistence",
		"persistence.dao",
		"persistence.binding.mapper",
		"service",

		"ui",
		"controller",
		"controller.servlet",
		"controller.binding.mapper",
		"controller.binding.dto"})

public class RootConfiguration {

}
