package example.demo.multiple.db.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

  private static final String PRIMARY_USERNAME = "PRIMARY";
  private static final String PRIMARY_PASSWORD = "PRIMARY";
  private static final String SECONDARY_USERNAME = "SECONDARY";
  private static final String SECONDARY_PASSWORD = "SECONDARY";

  public static final OracleContainer CONTAINER = new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"))
      .withEnv("APP_USER", PRIMARY_USERNAME)
      .withEnv("APP_USER_PASSWORD", PRIMARY_PASSWORD)
      .withUsername(PRIMARY_USERNAME)
      .withPassword(PRIMARY_PASSWORD)
      .withCopyFileToContainer(MountableFile.forClasspathResource("secondary-user.sql"), "/container-entrypoint-initdb.d/01-secondary-user.sql");

  static {
    CONTAINER.start();
  }

  @DynamicPropertySource
  static void registerDataSources(DynamicPropertyRegistry registry) {
    // Primary datasource
    registry.add("spring.datasource.primary.url", CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.primary.username", () -> PRIMARY_USERNAME);
    registry.add("spring.datasource.primary.password", () -> PRIMARY_PASSWORD);

    // Secondary datasource
    registry.add("spring.datasource.secondary.url", CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.secondary.username", () -> SECONDARY_USERNAME);
    registry.add("spring.datasource.secondary.password", () -> SECONDARY_PASSWORD);
  }

  @Bean
  public OracleContainer oracleContainer() {
    return CONTAINER;
  }

}
