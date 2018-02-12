package gov.hud.lrs.services.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Profile("dev")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = { "gov.hud.lrs.common.repository.ext" },
	entityManagerFactoryRef = "extEntityManagerFactory",
	transactionManagerRef = "extTransactionManager"
)
public class LrsExtDataConfig {

	@Bean
	@ConfigurationProperties("lrs.ext.datasource")
	public DataSource extDataSource() {
		return DataSourceBuilder.create().build();
 	}
	
	@Bean
	@ConfigurationProperties("lrs.ext.jpa")
	public JpaProperties extJpaProperties() {
		return new JpaProperties();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean extEntityManagerFactory(
		@Qualifier("extDataSource") DataSource extDataSource,
		@Qualifier("extJpaProperties") JpaProperties extJpaProperties
	) {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPersistenceUnitName("lrs.ext");
		localContainerEntityManagerFactoryBean.setDataSource(extDataSource);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] { "gov.hud.lrs.common.entity.ext" } );
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(extJpaProperties.getProperties());
		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager extTransactionManager(@Qualifier("extEntityManagerFactory") EntityManagerFactory extEntityManagerFactory) {
		return new JpaTransactionManager(extEntityManagerFactory);
	}

}
