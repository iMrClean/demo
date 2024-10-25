package example.demo.multipledb.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@SpringBootTest
public class TestcontainersConfiguration {


    public static OracleContainer oracleFreeContainer = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
            .withExposedPorts(1521)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                    new PortBinding(Ports.Binding.bindPort(1521), new ExposedPort(1521))
            )));


    @BeforeAll
    public static void setUp() {
        oracleFreeContainer.setWaitStrategy(
                new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(1)
                        .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS))
        );
        oracleFreeContainer.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", oracleFreeContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", oracleFreeContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", oracleFreeContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", oracleFreeContainer::getDriverClassName);
    }

}
