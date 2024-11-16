package example.demo.simple.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    private static final String USERNAME = "PRIMARY";
    private static final String PASSWORD = "PRIMARY";

    @Bean
    @ServiceConnection
    public OracleContainer oracleFreeContainer() {
        var container = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
                .withUsername(USERNAME)
                .withPassword(PASSWORD);

        container.start();

        return container;
    }

}
