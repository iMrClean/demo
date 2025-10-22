package example.demo.multiple.db;

import example.demo.multiple.db.config.PrimaryDataSourceConfiguration;
import example.demo.multiple.db.config.SecondaryDataSourceConfiguration;
import example.demo.multiple.db.config.TestcontainersConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@Import({TestcontainersConfiguration.class, PrimaryDataSourceConfiguration.class, SecondaryDataSourceConfiguration.class})
public @interface IntegrationTest { }