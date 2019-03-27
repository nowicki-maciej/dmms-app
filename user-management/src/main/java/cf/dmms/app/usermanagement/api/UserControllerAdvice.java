package cf.dmms.app.usermanagement.api;

import cf.dmms.app.usermanagement.api.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice(assignableTypes = {UserController.class})
public class UserControllerAdvice {

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity handleUserNotFoundException() {
		return new ResponseEntity(UNAUTHORIZED);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity handleDataIntegrityViolationException() {
		return new ResponseEntity(NOT_ACCEPTABLE);
	}
}
