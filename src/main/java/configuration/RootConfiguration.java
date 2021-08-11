package configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration

@ComponentScan(basePackages = { "persistence",
		"persistence.dao",
		"persistence.binding.mapper",
		"service",
		"ui",
		"controller",
		"controller.binding.mapper",
		"controller.binding.dto"})

public class RootConfiguration {
	
	private static final String PROP_FILE_NAME = "/datasource.properties";

	@Bean
	public HikariDataSource getDataSource() {
		HikariConfig config = new HikariConfig(PROP_FILE_NAME);
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return new HikariDataSource(config);
	}

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(getDataSource());
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
	
}
