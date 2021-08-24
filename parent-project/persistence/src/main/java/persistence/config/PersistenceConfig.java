package persistence.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"persistence.binding.mapper",
		"persistence.dao",
		
})
public class PersistenceConfig  {

	@Bean
	public SessionFactory getSessionFactory() {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml")
				.build();
		SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().buildSessionFactory( serviceRegistry );
		return sessionFactory;
	}
}
