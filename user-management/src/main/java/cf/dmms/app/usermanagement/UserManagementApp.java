package cf.dmms.app.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class UserManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApp.class, args);
    }

}
