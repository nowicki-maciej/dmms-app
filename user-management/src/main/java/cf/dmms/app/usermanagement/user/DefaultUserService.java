package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.FullUpdateUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;
import cf.dmms.app.usermanagement.api.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cf.dmms.app.usermanagement.user.UserMapper.mapToBasicUserDto;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public BasicUserDto findById(Long id) throws UserNotFoundException{
        User user = findUserById(id);
        return mapToBasicUserDto(user);
    }

    @Transactional
    public List<BasicUserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::mapToBasicUserDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public void register(RegistrationUserDto registrationUserDto) {
        User user = UserMapper.mapToUser(registrationUserDto, passwordEncoder);
        userRepository.save(user);
    }

    @Transactional
    public void updateCurrentUser(UpdateUserDto userDto) throws UserNotFoundException{
        User user = findUserById(userDto.getId());

        Optional.ofNullable(userDto.getDisplayName())
                .ifPresent(user::setDisplayName);
        Optional.ofNullable(userDto.getEmail())
                .ifPresent(user::setEmail);
        Optional.ofNullable(userDto.getOldPassword())
                .filter(oldPassword -> verifyPasswords(oldPassword, user.getPassword()))
                .ifPresent(oldPassword -> user.setPassword(userDto.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void updateUser(FullUpdateUserDto userDto) throws UserNotFoundException {
        User user = findUserById(userDto.getId());

        Optional.ofNullable(userDto.getDisplayName())
                .ifPresent(user::setDisplayName);
        Optional.ofNullable(userDto.getPassword())
                .ifPresent(user::setPassword);
        Optional.ofNullable(userDto.getEmail())
                .ifPresent(user::setEmail);
        Optional.of(userDto.getRole())
                .ifPresent(user::setRole);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private boolean verifyPasswords(String textPassword, String encodedPassword) {
        return encodedPassword.equals(passwordEncoder.encode(textPassword));
    }

}
