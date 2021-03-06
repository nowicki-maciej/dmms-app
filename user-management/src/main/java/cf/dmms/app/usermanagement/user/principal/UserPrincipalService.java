package cf.dmms.app.usermanagement.user.principal;

import cf.dmms.app.spi.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

	private UserRepository userRepository;

	public UserPrincipalService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with given login"));

		return new UserPrincipal(user);
	}

}
