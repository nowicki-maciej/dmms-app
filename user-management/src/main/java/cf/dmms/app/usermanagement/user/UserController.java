package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<BasicUserDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/find/{userLogin}")
    public ResponseEntity<BasicUserDto> findUserByLogin(@PathVariable("userLogin") String login) {
        return ResponseEntity.ok(userService.findByLogin(login));
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationUserDto userDto) {
        if(!passwordsAreEqual(userDto)) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        userService.register(userDto);
        return new ResponseEntity<>(OK);
    }

    private boolean passwordsAreEqual(RegistrationUserDto userDto) {
        return userDto.getPassword().equals(userDto.getRepeatedPassword());
    }

}
