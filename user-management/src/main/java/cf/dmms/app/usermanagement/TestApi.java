package cf.dmms.app.usermanagement;


import cf.dmms.app.usermanagement.user.Role;
import cf.dmms.app.usermanagement.user.User;
import cf.dmms.app.usermanagement.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO: remove
@RestController
@RequestMapping("/user-test-management")
public class TestApi {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testMethodWhatever() {
        return ResponseEntity.ok("Test is complete!");
    }

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
