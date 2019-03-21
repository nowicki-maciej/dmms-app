package cf.dmms.app.usermanagement.api;

import cf.dmms.app.usermanagement.user.UserService;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserManagementController {

    private UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/manage/{userId}")
//    public ResponseEntity manageUser(@PathVariable)


}
