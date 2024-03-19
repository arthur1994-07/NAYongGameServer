package com.common.nayong.configuration;

import com.common.nayong.entity.CharacterEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.common.nayong.repo.db2",
        entityManagerFactoryRef = "ranGameS1EntityManagerFactory",
        transactionManagerRef = "ranGameS1TransactionManager")
public class RanGameS1DbConfiguration {

    @Bean(name = "RanGameS1Properties")
    @ConfigurationProperties("spring.db2.datasource")
    public DataSourceProperties ranGameS1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "RanGameS1DataSource")
    @ConfigurationProperties("spring.db2.datasource.configuration")
    public DataSource ranGameS1DataSource() {
        return ranGameS1DataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "ranGameS1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ranGameS1EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(ranGameS1DataSource())
                .packages(CharacterEntity.class)
                .build();
    }

    @Bean(name = "ranGameS1TransactionManager")
    public PlatformTransactionManager ranGameS1TransactionManager(final @Qualifier("ranGameS1EntityManagerFactory")
                                                                LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
