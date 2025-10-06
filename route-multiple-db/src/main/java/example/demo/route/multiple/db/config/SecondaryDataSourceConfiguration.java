package example.demo.route.multiple.db.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "example.demo.route.multiple.db.repository.secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfiguration {

    private static final String PACKAGES = "example.demo.route.multiple.db.domain.secondary";

    private static final String PERSISTENCE_UNIT = "secondary";

    @Bean
    @ConfigurationProperties("spring.datasource.secondary-a")
    public DataSourceProperties secondaryADataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.secondary-b")
    public DataSourceProperties secondaryBDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondaryADataSource(@Qualifier("secondaryADataSourceProperties") DataSourceProperties secondaryADataSourceProperties) {
        return secondaryADataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSource secondaryBDataSource(@Qualifier("secondaryBDataSourceProperties") DataSourceProperties secondaryBDataSourceProperties) {
        return secondaryBDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSource secondaryRoutingDataSource(@Qualifier("secondaryADataSource") DataSource secondaryADataSource, @Qualifier("secondaryBDataSource") DataSource secondaryBDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.SECONDARY_A, secondaryADataSource);
        targetDataSources.put(DatabaseType.SECONDARY_B, secondaryBDataSource);

        DatabaseTypeRoutingDataSource routingDataSource = new DatabaseTypeRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(secondaryADataSource);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("secondaryRoutingDataSource") DataSource routingDataSource) {
        return builder
                .dataSource(routingDataSource)
                .packages(PACKAGES)
                .persistenceUnit(PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public TransactionTemplate secondaryTransactionTemplate(@Qualifier("secondaryTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
