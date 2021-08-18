package configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
		"persistence.dto",
		"persistence.binding.mapper",
		"service",
		"ui",
		"controller",
		"controller.binding.mapper",
"controller.binding.dto"})

public class RootConfiguration {

	private static final String PROP_HIKARI_FILE_NAME = "/datasource.properties";
	private static final String PROP_HIBERNATE_FILE_NAME = "/hibernate.properties";

	@Bean
	public HikariDataSource getDataSource() {
		HikariConfig config = new HikariConfig(PROP_HIKARI_FILE_NAME);
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

	@Bean
	public SessionFactory getSessionFactory() {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml")
				.build();
		SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().buildSessionFactory( serviceRegistry );
		return sessionFactory;
		/*
		org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
		SessionFactory sessionFactory = config.buildSessionFactory();
		return sessionFactory;*/
	}


}
