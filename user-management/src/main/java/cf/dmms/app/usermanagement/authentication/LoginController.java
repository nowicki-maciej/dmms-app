package cf.dmms.app.usermanagement.authentication;

import cf.dmms.app.usermanagement.security.jwt.JwtAuthenticationResponse;
import cf.dmms.app.usermanagement.security.jwt.JwtTokenProvider;
import cf.dmms.app.usermanagement.user.Role;
import cf.dmms.app.usermanagement.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/user-management")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    //TODO: catch BadCredentialsException
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationForUser(loginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    private Authentication authenticationForUser(LoginDto loginDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getPassword()
                )
        );
    }

    //TODO: remove
    @GetMapping("/createUser")
    public ResponseEntity<String> createUser() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setDisplayName("name");
        user.setEmail("email@example.com");
        user.setRole(Role.USER);

        userRepository.save(user);

        return ResponseEntity.ok("dodano testowego użytkownika");
    }

}
