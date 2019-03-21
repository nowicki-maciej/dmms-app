package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static cf.dmms.app.usermanagement.user.UserMapper.mapToBasicUserDto;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public BasicUserDto findByLogin(String login) throws UserNotFoundException{
        User user = userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);
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
}
