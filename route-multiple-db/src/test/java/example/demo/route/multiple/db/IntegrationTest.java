package example.demo.route.multiple.db;

import example.demo.route.multiple.db.config.TestcontainersConfiguration;
import example.demo.route.multiple.db.config.TestcontainersInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public @interface IntegrationTest { }