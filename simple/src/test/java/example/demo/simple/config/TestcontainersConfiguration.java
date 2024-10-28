package example.demo.simple.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    public static final String USERNAME = "PRIMARY";
    public static final String PASSWORD = "PRIMARY";
    public static final int PORT = 1521;

    @Bean
    @ServiceConnection
    public OracleContainer oracleFreeContainer() {
        var oracle = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(PORT)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(PORT), new ExposedPort(PORT)))));

        oracle.start();
        log.info(oracle.getLogs());
        return oracle;
    }

}
