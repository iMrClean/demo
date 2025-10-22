package example.demo.route.multiple.db.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;

import javax.sql.DataSource;

@TestConfiguration(proxyBeanMethods = false)
public class SecondaryDataSourceConfiguration {

    @Bean
    public DataSource secondaryADataSource(OracleContainer container) {
        var cfg = new HikariConfig();

        cfg.setJdbcUrl(container.getJdbcUrl());
        cfg.setUsername(container.getUsername());
        cfg.setPassword(container.getPassword());

        return new HikariDataSource(cfg);
    }

    @Bean
    public DataSource secondaryBDataSource(OracleContainer container) {
        var cfg = new HikariConfig();

        cfg.setJdbcUrl(container.getJdbcUrl());
        cfg.setUsername(container.getUsername());
        cfg.setPassword(container.getPassword());

        return new HikariDataSource(cfg);
    }

}
