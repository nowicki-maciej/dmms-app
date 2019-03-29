package cf.dmms.app.usermanagement.api.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = { LoginController.class })
public class LoginControllerAdvice {

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity handleBadCredentialsException() {
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
}
