package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(assignableTypes = { UserController.class })
public class UserControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity handleUserNotFoundException() {
        return new ResponseEntity(NOT_FOUND);
    }

}
