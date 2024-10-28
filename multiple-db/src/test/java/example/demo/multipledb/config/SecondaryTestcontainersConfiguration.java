package example.demo.multipledb.config;

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
public class SecondaryTestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public OracleContainer oracleFreeContainerSecondary() {
        OracleContainer oracle = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
                .withUsername("SECONDARY")
                .withPassword("SECONDARY")
                .withExposedPorts(1521) // Указываем порт контейнера
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                        new PortBinding(Ports.Binding.bindPort(1521), new ExposedPort(1521))
                )));

        oracle.start();
        log.info(oracle.getLogs());

        return oracle;
    }

}
