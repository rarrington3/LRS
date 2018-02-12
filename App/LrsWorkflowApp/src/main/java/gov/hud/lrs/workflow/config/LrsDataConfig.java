package gov.hud.lrs.workflow.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = { "gov.hud.lrs.common.repository" },
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager"
)
public class LrsDataConfig {

	@Bean
	@Primary
	@ConfigurationProperties("lrs.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
 	}
	
	@Bean
	@Primary
	@ConfigurationProperties("lrs.jpa")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
		DataSource dataSource,
		@Qualifier("jpaProperties") JpaProperties jpaProperties
	) {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPersistenceUnitName("lrs");
		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] { "gov.hud.lrs.common.entity" } );
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());
		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(
		@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
	) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
