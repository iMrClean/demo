package example.demo.multiple.db.config;

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
        "spring.datasource.secondary.url=" + container.getJdbcUrl(),
        "spring.datasource.secondary.username=SECONDARY",
        "spring.datasource.secondary.password=SECONDARY"
    );
  }

}