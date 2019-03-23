package cf.dmms.app.usermanagement.api;

import cf.dmms.app.usermanagement.user.UserService;
import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.dto.RoleUpdateUserDto;
import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;
import cf.dmms.app.usermanagement.user.principal.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
//TODO: Add basic mockMvc tests
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
    public ResponseEntity updateCurrentUser(@RequestBody UpdateUserDto userDto) {
        userDto.setId(currentUser().getId());
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
    public ResponseEntity register(@Valid @RequestBody RegistrationUserDto userDto) {
        userService.register(userDto);
        return new ResponseEntity<>(OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/role")
    public ResponseEntity updateUser(@Valid @RequestBody RoleUpdateUserDto userDto) {
        if(currentUser().getId().equals(userDto.getId())) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        userService.updateUserRole(userDto);
        return new ResponseEntity<>(OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId) {
        if(currentUser().getId().equals(userId)) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>(OK);
    }

    private UserPrincipal currentUser() {
        return (UserPrincipal)SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
    }

}
