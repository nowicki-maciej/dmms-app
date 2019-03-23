package cf.dmms.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "cf.dmms.app")
@EnableJpaRepositories("cf.dmms.app")
@EntityScan("cf.dmms.app")
@PropertySource("classpath:web-app.properties")
public class DmmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmmsApplication.class, args);
	}
}
