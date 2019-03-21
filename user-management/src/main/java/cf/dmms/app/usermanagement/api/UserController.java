package cf.dmms.app.usermanagement.api;

import cf.dmms.app.usermanagement.user.UserService;
import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.FullUpdateUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;
import cf.dmms.app.usermanagement.api.exception.PasswordsMismatchException;
import cf.dmms.app.usermanagement.user.principal.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public ResponseEntity<BasicUserDto> getCurrentUser(){
        UserPrincipal userPrincipal = currentUser();
        return ResponseEntity.ok(userService.findById(userPrincipal.getId()));
    }

    @PutMapping("/current")
    public ResponseEntity updateCurrentUser(@RequestBody UpdateUserDto userDto) throws PasswordsMismatchException {
        userDto.setId(currentUser().getId());
        if(nonNull(userDto.getOldPassword())) {
            verifyPasswords(userDto.getPassword(), userDto.getRepeatedPassword());
        }
        userService.updateCurrentUser(userDto);
        return new ResponseEntity<>(OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BasicUserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<BasicUserDto> findUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationUserDto userDto) throws PasswordsMismatchException {
        verifyPasswords(userDto.getPassword(), userDto.getRepeatedPassword());

        userService.register(userDto);
        return new ResponseEntity<>(OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody FullUpdateUserDto userDto) throws PasswordsMismatchException{
        if(currentUser().getId().equals(userDto.getId())) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        Optional.ofNullable(userDto.getPassword())
                .ifPresent(password -> verifyPasswords(password, userDto.getRepeatedPassword()));
        userService.updateUser(userDto);
        return new ResponseEntity<>(OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId) {
        if(currentUser().getId().equals(userId)) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>(OK);
    }

    private void verifyPasswords(String password, String repeatedPassword) throws PasswordsMismatchException {
        if(!password.equals(repeatedPassword)) {
            throw new PasswordsMismatchException();
        }
    }

    private UserPrincipal currentUser() {
        return (UserPrincipal)SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
    }

}
