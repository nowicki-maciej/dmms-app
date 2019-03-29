package cf.dmms.app.core.book;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackages = "cf.dmms.app.core")
@EnableJpaRepositories(basePackages = "cf.dmms.app.core")
@EntityScan(basePackages = "cf.dmms.app.core")
@TestPropertySource("classpath:dmms-core.properties")
public abstract class IntegrationTestBase {
}
