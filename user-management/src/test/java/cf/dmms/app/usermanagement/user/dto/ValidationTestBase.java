package cf.dmms.app.usermanagement.user.dto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ValidationTestBase {

	static final String LOGIN = "login";
	static final String VALID_PASSWORD = "valid_password";
	static final String VALID_EMAIL = "valid@email.com";
	static final String DISPLAY_NAME = "display-name";

	static final String PASSWORDS_MISMATCH_ERROR_MESSAGE = "passwords must be the same";
	static final String MAIL_VALIDATION_ERROR_MESSAGE = "must be a well-formed email address";
	static final String BLANK_FIELD_ERROR_MESSAGE = "must not be blank";

	final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	static List<String> extractMessages(Collection<ConstraintViolation<Object>> validationResults) {
		return validationResults.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
	}
}
