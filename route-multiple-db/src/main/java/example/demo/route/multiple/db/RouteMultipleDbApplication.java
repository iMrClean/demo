package example.demo.route.multiple.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RouteMultipleDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteMultipleDbApplication.class, args);
    }

}
