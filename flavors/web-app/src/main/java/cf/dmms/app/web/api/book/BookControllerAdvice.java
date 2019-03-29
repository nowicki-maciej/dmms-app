package cf.dmms.app.web.api.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(assignableTypes = { BookController.class })
class BookControllerAdvice {

	@ExceptionHandler({ EntityNotFoundException.class })
	ResponseEntity<?> handleEntityNotFoundException() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
