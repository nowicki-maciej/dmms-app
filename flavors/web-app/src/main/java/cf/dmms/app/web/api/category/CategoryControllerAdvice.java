package cf.dmms.app.web.api.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(assignableTypes = { CategoryController.class })
class CategoryControllerAdvice {

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<?> handleEntityNotFoundException() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
