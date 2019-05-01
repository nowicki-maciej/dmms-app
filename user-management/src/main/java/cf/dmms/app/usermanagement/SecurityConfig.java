package cf.dmms.app.usermanagement;

import cf.dmms.app.usermanagement.security.UnauthorizedHandler;
import cf.dmms.app.usermanagement.user.principal.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserPrincipalService userPrincipalService;
	private UnauthorizedHandler unauthorizedHandler;

	public SecurityConfig(UserPrincipalService userPrincipalService, UnauthorizedHandler unauthorizedHandler) {
		this.userPrincipalService = userPrincipalService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userPrincipalService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.logout()
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutUrl("/user-management/logout")
				.logoutSuccessHandler(logoutSuccessHandler())
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
				.authorizeRequests()
				.antMatchers("/user-management/login", "/user-test-management/createUser")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/categories")
				.hasAnyRole("ADMIN", "USER")
				.antMatchers("/logs/**", "/categories/**")
				.hasRole("ADMIN")
				.anyRequest()
				.authenticated();
	}

	//changing default spring security behaviour
	private LogoutSuccessHandler logoutSuccessHandler() {
		return (request, response, authentication) -> response.setStatus(200);
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
