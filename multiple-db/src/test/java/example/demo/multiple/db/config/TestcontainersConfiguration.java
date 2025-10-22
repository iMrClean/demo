package example.demo.multiple.db.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

  private static final String PRIMARY_USERNAME = "PRIMARY";
  private static final String PRIMARY_PASSWORD = "PRIMARY";

  public static final OracleContainer CONTAINER = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
          .withUsername(PRIMARY_USERNAME)
          .withPassword(PRIMARY_PASSWORD)
          .withCopyFileToContainer(MountableFile.forClasspathResource("secondary-user.sql"), "/container-entrypoint-initdb.d/01-secondary-user.sql")
          .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("OracleContainer")));

  static {
    CONTAINER.start();
  }

  @Bean
  public OracleContainer oracleContainer() {
    return CONTAINER;
  }

}
