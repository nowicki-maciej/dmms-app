package cf.dmms.app.usermanagement.user;

import cf.dmms.app.spi.user.Role;
import cf.dmms.app.spi.user.User;
import cf.dmms.app.usermanagement.api.exception.UserNotFoundException;
import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.dto.RoleUpdateUserDto;
import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;
import cf.dmms.app.usermanagement.user.principal.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cf.dmms.app.usermanagement.user.UserMapper.toDto;

@Service
public class DefaultUserService implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public BasicUserDto findCurrentUser() throws UserNotFoundException {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();

		User user = findUserById(principal.getId());
		return toDto(user);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public BasicUserDto findById(Long id) throws UserNotFoundException {
		User user = findUserById(id);
		return toDto(user);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public List<BasicUserDto> findAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream()
				.map(UserMapper::toDto)
				.collect(Collectors.toList());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void register(RegistrationUserDto registrationUserDto) {
		User user = UserMapper.toEntity(registrationUserDto, passwordEncoder);
		userRepository.save(user);
	}

	@Override
	public void updateCurrentUser(UpdateUserDto userDto) throws UserNotFoundException {
		User user = findUserById(userDto.getId());

		Optional.ofNullable(userDto.getDisplayName())
				.filter(DefaultUserService::notEmpty)
				.ifPresent(user::setDisplayName);
		Optional.ofNullable(userDto.getEmail())
				.filter(DefaultUserService::notEmpty)
				.ifPresent(user::setEmail);
		Optional.ofNullable(userDto.getOldPassword())
				.filter(DefaultUserService::notEmpty)
				.filter(oldPassword -> verifyPasswords(oldPassword, user.getPassword()))
				.ifPresent(oldPassword -> user.setPassword(userDto.getPassword()));

		userRepository.save(user);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void updateUserRole(RoleUpdateUserDto userDto) throws UserNotFoundException {
		User user = findUserById(userDto.getId());

		Optional.of(userDto.getRole())
				.ifPresent(user::setRole);

		userRepository.save(user);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(UserNotFoundException::new);
	}

	@PostConstruct
	public void initUser() {
		if (!userRepository.findByLogin("admin").isPresent()) {
			RegistrationUserDto registrationUserDto = new RegistrationUserDto(
					"admin",
					"admin",
					"admin",
					"Administrator",
					"admin@example.com",
					Role.ROLE_ADMIN
			);
			User user = UserMapper.toEntity(registrationUserDto, passwordEncoder);
			userRepository.save(user);
		}
	}

	private boolean verifyPasswords(String textPassword, String encodedPassword) {
		return encodedPassword.equals(passwordEncoder.encode(textPassword));
	}

	private static boolean notEmpty(String s) {
		return !s.isEmpty();
	}
}
