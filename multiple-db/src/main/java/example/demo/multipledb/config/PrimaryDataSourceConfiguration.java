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
import org.springframework.context.annotation.Primary;
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
        basePackages = "example.demo.multipledb.repository.primary",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfiguration {

    private static final String PACKAGES = "example.demo.multipledb.domain.primary";

    private static final String PERSISTENCE_UNIT = "primary";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource())
                .packages(PACKAGES)
                .persistenceUnit(PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @Primary
    public TransactionTemplate primaryTransactionTemplate(@Qualifier("primaryTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary.liquibase")
    public LiquibaseProperties primaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase primaryLiquibase(@Qualifier("primaryDataSource") DataSource primaryDataSource, @Qualifier("primaryLiquibaseProperties") LiquibaseProperties primaryLiquibaseProperties) {
        var liquibase = new SpringLiquibase();

        liquibase.setDataSource(primaryDataSource);

        liquibase.setChangeLog(primaryLiquibaseProperties.getChangeLog());
        liquibase.setClearCheckSums(primaryLiquibaseProperties.isClearChecksums());
        liquibase.setContexts(primaryLiquibaseProperties.getContexts());
        liquibase.setDefaultSchema(primaryLiquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(primaryLiquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(primaryLiquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(primaryLiquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(primaryLiquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(primaryLiquibaseProperties.isDropFirst());
        liquibase.setShouldRun(primaryLiquibaseProperties.isEnabled());
        liquibase.setLabelFilter(primaryLiquibaseProperties.getLabelFilter());
        liquibase.setChangeLogParameters(primaryLiquibaseProperties.getParameters());
        liquibase.setRollbackFile(primaryLiquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(primaryLiquibaseProperties.isTestRollbackOnUpdate());
        liquibase.setTag(primaryLiquibaseProperties.getTag());
        Optional.ofNullable(primaryLiquibaseProperties.getShowSummary()).map(Enum::name).map(UpdateSummaryEnum::valueOf).ifPresent(liquibase::setShowSummary);
        Optional.ofNullable(primaryLiquibaseProperties.getShowSummaryOutput()).map(Enum::name).map(UpdateSummaryOutputEnum::valueOf).ifPresent(liquibase::setShowSummaryOutput);
        Optional.ofNullable(primaryLiquibaseProperties.getUiService()).map(Enum::name).map(UIServiceEnum::valueOf).ifPresent(liquibase::setUiService);

        return liquibase;
    }

}
