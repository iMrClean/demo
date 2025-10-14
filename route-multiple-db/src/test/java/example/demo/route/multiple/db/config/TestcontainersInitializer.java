package example.demo.route.multiple.db.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    var container = TestcontainersConfiguration.CONTAINER;

    TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "spring.datasource.primary.url=" + container.getJdbcUrl(),
        "spring.datasource.primary.username=PRIMARY",
        "spring.datasource.primary.password=PRIMARY",
        "spring.datasource.secondary-a.url=" + container.getJdbcUrl(),
        "spring.datasource.secondary-a.username=SECONDARY-A",
        "spring.datasource.secondary-a.password=SECONDARY-A",
        "spring.datasource.secondary-b.url=" + container.getJdbcUrl(),
        "spring.datasource.secondary-b.username=SECONDARY-B",
        "spring.datasource.secondary-b.password=SECONDARY-B"
    );
  }

}