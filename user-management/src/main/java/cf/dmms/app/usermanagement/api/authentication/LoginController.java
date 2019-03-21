package cf.dmms.app.usermanagement.api.authentication;

import cf.dmms.app.usermanagement.user.UserRepository;
import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cf.dmms.app.usermanagement.user.UserMapper.mapToBasicUserDto;

@RestController
@RequestMapping("/user-management")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<BasicUserDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationForUser(loginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(getUserDtoFrom(loginDto.getLogin()));
    }

    private Authentication authenticationForUser(LoginDto loginDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getPassword()
                )
        );
    }

    private BasicUserDto getUserDtoFrom(String login) {
        return mapToBasicUserDto(userRepository.getOneByLogin(login));
    }
}
