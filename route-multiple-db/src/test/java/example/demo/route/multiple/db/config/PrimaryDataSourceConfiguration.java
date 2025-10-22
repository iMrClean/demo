package example.demo.route.multiple.db.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.oracle.OracleContainer;

import javax.sql.DataSource;

@TestConfiguration(proxyBeanMethods = false)
public class PrimaryDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource primaryDataSource(OracleContainer container) {
        var cfg = new HikariConfig();

        cfg.setJdbcUrl(container.getJdbcUrl());
        cfg.setUsername(container.getUsername());
        cfg.setPassword(container.getPassword());

        return new HikariDataSource(cfg);
    }

}
