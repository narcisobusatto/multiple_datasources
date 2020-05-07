package br.com.spring_data_jpa.multiple_datasources.datasources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager", basePackages = {
		"br.com.spring_data_jpa.multiple_datasources.datasources.mysql.repositories" })
@PropertySource("classpath:/application.properties")
public class MysqlDBConfig {

	protected static String PROPERTY_NAME = "spring.second-datasource.";
	
	@Autowired
	private Environment env;

	@Bean(name = "mysqlDataSource")
	DataSource mysqlDataSource() {
		return DataSourceBuilder.create().driverClassName(env.getProperty(PROPERTY_NAME + "driverClassName"))
				.url(env.getProperty(PROPERTY_NAME +"url"))
				.username(env.getProperty(PROPERTY_NAME + "username"))
				.password(env.getProperty(PROPERTY_NAME + "password"))
				.build();
	}

	@SuppressWarnings("rawtypes")
	@Bean(name = "mysqlEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("mysqlDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		MutablePropertySources propSrcs = ((AbstractEnvironment) env).getPropertySources();
		StreamSupport.stream(propSrcs.spliterator(), false)
			.filter(ps -> ps instanceof EnumerablePropertySource)
			.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
			.flatMap(Arrays::<String>stream)
			.distinct()
			.filter(ps -> ps.contains(PROPERTY_NAME) && !(ps.contains("username") || ps.contains("password")))
			.forEach(propName -> properties.put(propName.split(PROPERTY_NAME)[1], env.getProperty(propName)));
		
		LocalContainerEntityManagerFactoryBean em = builder.dataSource(dataSource)
				.packages("br.com.spring_data_jpa.multiple_datasources.datasources.mysql.models").persistenceUnit("mysql").build();

		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean(name = "mysqlTransactionManager")
	PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
		return new JpaTransactionManager(mysqlEntityManagerFactory);
	}
	
}