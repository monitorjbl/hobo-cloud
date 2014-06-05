package com.thundermoose.hobo.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Configuration
@Import(PropertiesConfig.class)
@EnableJpaRepositories(basePackages = {"com.thundermoose.hobo.persistence"})
@EnableTransactionManagement
public class JPAConfig {
  @Value("${db.jdbc}")
  String jdbc;
  @Value("${db.user}")
  String user;
  @Value("${db.pass}")
  String pass;
  @Value("${db.logSql}")
  boolean showSql;

  @Bean
  public DataSource dataSource() {
    JdbcDataSource ds = new JdbcDataSource();
    ds.setURL(jdbc);
    ds.setUser(user);
    ds.setPassword(pass);
    return ds;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setShowSql(showSql);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.thundermoose.hobo.model");
    factory.afterPropertiesSet();

    return factory.getObject();
  }

  @Bean
  public EntityManager entityManager(EntityManagerFactory factory) {
    return factory.createEntityManager();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager mgr = new JpaTransactionManager();
    mgr.setEntityManagerFactory(entityManagerFactory());
    return mgr;
  }

  @Bean
  public HibernateExceptionTranslator exceptionTranslator(){
    return new HibernateExceptionTranslator();
  }
}
