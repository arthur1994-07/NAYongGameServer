package com.common.nayong.configuration;

import com.common.nayong.entity.GSUserEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
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
@EnableJpaRepositories(basePackages = "com.common.nayong.repo.db1",
        entityManagerFactoryRef = "ranUserEntityManagerFactory",
        transactionManagerRef = "ranUserTransactionManager"
)
public class RanUserDbConfiguration {

    @Bean(name = "RanUserProperties")
    @Primary
    @ConfigurationProperties("spring.db1.datasource")
    public DataSourceProperties ranUserDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "RanUserDataSource")
    @Primary
    @ConfigurationProperties("spring.db1.datasource.configuration")
    public DataSource ranUserDataSource() {
        return ranUserDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "ranUserEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean ranUserEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(ranUserDataSource())
                .packages(GSUserEntity.class)
                .build();
    }

    @Bean(name = "ranUserTransactionManager")
    @Primary
    public PlatformTransactionManager ranUserTransactionManager(final @Qualifier("ranUserEntityManagerFactory")
                                                                    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
