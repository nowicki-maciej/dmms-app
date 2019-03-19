package cf.dmms.app.usermanagement;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user-test-management")

public class TestApi {

    @GetMapping("/user")
    public String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity<String> testMethodWhatever() {
        return ResponseEntity.ok("Test is complete!");
    }
}
