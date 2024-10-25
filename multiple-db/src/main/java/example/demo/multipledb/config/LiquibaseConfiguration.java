//package example.demo.multipledb.config;
//
//import liquibase.UpdateSummaryEnum;
//import liquibase.UpdateSummaryOutputEnum;
//import liquibase.integration.spring.SpringLiquibase;
//import liquibase.ui.UIServiceEnum;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Optional;
//
//@Configuration
//public class LiquibaseConfiguration {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.primary.liquibase")
//    public LiquibaseProperties primaryLiquibaseProperties() {
//        return new LiquibaseProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.secondary.liquibase")
//    public LiquibaseProperties secondaryLiquibaseProperties() {
//        return new LiquibaseProperties();
//    }
//
//    @Bean
//    public SpringLiquibase primaryLiquibase(@Qualifier("primaryDataSource") DataSource primaryDataSource, @Qualifier("primaryLiquibaseProperties") LiquibaseProperties primaryLiquibaseProperties) {
//        return createSpringLiquibase(primaryDataSource, primaryLiquibaseProperties);
//    }
//
//    @Bean
//    public SpringLiquibase secondaryLiquibase(@Qualifier("secondaryDataSource") DataSource secondaryDataSource, @Qualifier("secondaryLiquibaseProperties") LiquibaseProperties secondaryLiquibaseProperties) {
//        return createSpringLiquibase(secondaryDataSource, secondaryLiquibaseProperties);
//    }
//
//    private SpringLiquibase createSpringLiquibase(@Qualifier("primaryDataSource") DataSource primaryDataSource, @Qualifier("primaryLiquibaseProperties") LiquibaseProperties primaryLiquibaseProperties) {
//        var liquibase = new SpringLiquibase();
//
//        liquibase.setDataSource(primaryDataSource);
//
//        liquibase.setChangeLog(primaryLiquibaseProperties.getChangeLog());
//        liquibase.setClearCheckSums(primaryLiquibaseProperties.isClearChecksums());
//        liquibase.setContexts(primaryLiquibaseProperties.getContexts());
//        liquibase.setDefaultSchema(primaryLiquibaseProperties.getDefaultSchema());
//        liquibase.setLiquibaseSchema(primaryLiquibaseProperties.getLiquibaseSchema());
//        liquibase.setLiquibaseTablespace(primaryLiquibaseProperties.getLiquibaseTablespace());
//        liquibase.setDatabaseChangeLogTable(primaryLiquibaseProperties.getDatabaseChangeLogTable());
//        liquibase.setDatabaseChangeLogLockTable(primaryLiquibaseProperties.getDatabaseChangeLogLockTable());
//        liquibase.setDropFirst(primaryLiquibaseProperties.isDropFirst());
//        liquibase.setShouldRun(primaryLiquibaseProperties.isEnabled());
//        liquibase.setLabelFilter(primaryLiquibaseProperties.getLabelFilter());
//        liquibase.setChangeLogParameters(primaryLiquibaseProperties.getParameters());
//        liquibase.setRollbackFile(primaryLiquibaseProperties.getRollbackFile());
//        liquibase.setTestRollbackOnUpdate(primaryLiquibaseProperties.isTestRollbackOnUpdate());
//        liquibase.setTag(primaryLiquibaseProperties.getTag());
//        Optional.ofNullable(primaryLiquibaseProperties.getShowSummary()).map(Enum::name).map(UpdateSummaryEnum::valueOf).ifPresent(liquibase::setShowSummary);
//        Optional.ofNullable(primaryLiquibaseProperties.getShowSummaryOutput()).map(Enum::name).map(UpdateSummaryOutputEnum::valueOf).ifPresent(liquibase::setShowSummaryOutput);
//        Optional.ofNullable(primaryLiquibaseProperties.getUiService()).map(Enum::name).map(UIServiceEnum::valueOf).ifPresent(liquibase::setUiService);
//
//        return liquibase;
//    }
//
//}
