package example.demo.multipledb.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.ui.UIServiceEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "example.demo.multipledb.repository.secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfiguration {

    private static final String PACKAGES = "example.demo.multipledb.domain.secondary";

    private static final String PERSISTENCE_UNIT = "secondary";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondaryDataSource() {
        return secondaryDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondaryDataSource())
                .packages(PACKAGES)
                .persistenceUnit(PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
        return new JpaTransactionManager(secondaryEntityManagerFactory);
    }

    @Bean
    public TransactionTemplate secondaryTransactionTemplate(@Qualifier("secondaryTransactionManager") PlatformTransactionManager secondaryTransactionManager) {
        return new TransactionTemplate(secondaryTransactionManager);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary.liquibase")
    public LiquibaseProperties secondaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase secondaryLiquibase(@Qualifier("secondaryDataSource") DataSource secondaryDataSource, @Qualifier("secondaryLiquibaseProperties") LiquibaseProperties secondaryLiquibaseProperties) {
        var liquibase = new SpringLiquibase();

        liquibase.setDataSource(secondaryDataSource);

        liquibase.setChangeLog(secondaryLiquibaseProperties.getChangeLog());
        liquibase.setClearCheckSums(secondaryLiquibaseProperties.isClearChecksums());
        liquibase.setContexts(secondaryLiquibaseProperties.getContexts());
        liquibase.setDefaultSchema(secondaryLiquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(secondaryLiquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(secondaryLiquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(secondaryLiquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(secondaryLiquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(secondaryLiquibaseProperties.isDropFirst());
        liquibase.setShouldRun(secondaryLiquibaseProperties.isEnabled());
        liquibase.setLabelFilter(secondaryLiquibaseProperties.getLabelFilter());
        liquibase.setChangeLogParameters(secondaryLiquibaseProperties.getParameters());
        liquibase.setRollbackFile(secondaryLiquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(secondaryLiquibaseProperties.isTestRollbackOnUpdate());
        liquibase.setTag(secondaryLiquibaseProperties.getTag());
        Optional.ofNullable(secondaryLiquibaseProperties.getShowSummary()).map(Enum::name).map(UpdateSummaryEnum::valueOf).ifPresent(liquibase::setShowSummary);
        Optional.ofNullable(secondaryLiquibaseProperties.getShowSummaryOutput()).map(Enum::name).map(UpdateSummaryOutputEnum::valueOf).ifPresent(liquibase::setShowSummaryOutput);
        Optional.ofNullable(secondaryLiquibaseProperties.getUiService()).map(Enum::name).map(UIServiceEnum::valueOf).ifPresent(liquibase::setUiService);

        return liquibase;
    }

}
