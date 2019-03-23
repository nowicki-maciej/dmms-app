package cf.dmms.app.core.author;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(assignableTypes = { AuthorController.class })
public class AuthorControllerAdvice {

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<?> handleEntityNotFoundException() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
