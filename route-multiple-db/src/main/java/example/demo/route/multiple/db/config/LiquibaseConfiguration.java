package example.demo.route.multiple.db.config;

import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.ui.UIServiceEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Optional;

@Configuration
public class LiquibaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary.liquibase")
    public LiquibaseProperties primaryLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary-a.liquibase")
    public LiquibaseProperties secondaryALiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary-b.liquibase")
    public LiquibaseProperties secondaryBLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase primaryLiquibase(@Qualifier("primaryDataSource") DataSource primaryDataSource, @Qualifier("primaryLiquibaseProperties") LiquibaseProperties properties) {
        return createSpringLiquibase(primaryDataSource, properties);
    }

    @Bean
    public SpringLiquibase secondaryALiquibase(@Qualifier("secondaryADataSource") DataSource secondaryADataSource, @Qualifier("secondaryALiquibaseProperties") LiquibaseProperties secondaryALiquibaseProperties) {
        return createSpringLiquibase(secondaryADataSource, secondaryALiquibaseProperties);
    }

    @Bean
    public SpringLiquibase secondaryBLiquibase(@Qualifier("secondaryBDataSource") DataSource secondaryBDataSource, @Qualifier("secondaryBLiquibaseProperties") LiquibaseProperties secondaryBLiquibaseProperties) {
        return createSpringLiquibase(secondaryBDataSource, secondaryBLiquibaseProperties);
    }

    private SpringLiquibase createSpringLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);

        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(String.join(",", Optional.ofNullable(properties.getContexts()).orElse(Collections.emptyList())));
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setLiquibaseSchema(properties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(properties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabelFilter(String.join(",", Optional.ofNullable(properties.getLabelFilter()).orElse(Collections.emptyList())));
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(properties.isTestRollbackOnUpdate());
        liquibase.setTag(properties.getTag());

        Optional.ofNullable(properties.getShowSummary()).map(Enum::name).map(UpdateSummaryEnum::valueOf).ifPresent(liquibase::setShowSummary);
        Optional.ofNullable(properties.getShowSummaryOutput()).map(Enum::name).map(UpdateSummaryOutputEnum::valueOf).ifPresent(liquibase::setShowSummaryOutput);
        Optional.ofNullable(properties.getUiService()).map(Enum::name).map(UIServiceEnum::valueOf).ifPresent(liquibase::setUiService);

        return liquibase;
    }

}
