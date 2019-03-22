package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ValidationTest {

    private static final String PASSWORDS_MISMATCH_ERROR = "passwords must be the same!";

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldNotValidatePasswords() {
        List<ConstraintViolation<RegistrationUserDto>> validationResults = new ArrayList<>();

        RegistrationUserDto registrationUserDto = new RegistrationUserDto(
                "login",
                "password",
                "rpassword",
                "display",
                "mail@example.com",
                Role.ADMIN
        );

        CollectionUtils.addAll(validationResults, validator.validate(registrationUserDto));
        assertThat(validationResults.get(0).getMessage()).isEqualTo(PASSWORDS_MISMATCH_ERROR);
    }
}