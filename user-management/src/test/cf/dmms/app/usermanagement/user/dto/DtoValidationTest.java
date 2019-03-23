package cf.dmms.app.usermanagement.user.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

import static cf.dmms.app.usermanagement.user.Role.ADMIN;
import static org.apache.commons.collections4.CollectionUtils.addAll;
import static org.assertj.core.api.Assertions.assertThat;


public class DtoValidationTest extends ValidationTestBase {

    private List<ConstraintViolation<Object>> validationResults;

    @Before
    public void setup() {
        validationResults = new ArrayList<>();
    }

    @Test
    public void shouldValidateRegisterDtoWithFullData() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto(
                LOGIN,
                VALID_PASSWORD,
                VALID_PASSWORD,
                DISPLAY_NAME,
                VALID_EMAIL,
                ADMIN
        );

        addAll(validationResults, validator.validate(registrationUserDto));
        assertThat(extractMessages(validationResults))
                .isEmpty();
    }

    @Test
    public void shouldNotValidateRegisterDtoWithBlankLogin() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto(
                "",
                VALID_PASSWORD,
                VALID_PASSWORD,
                DISPLAY_NAME,
                VALID_EMAIL,
                ADMIN
        );

        addAll(validationResults, validator.validate(registrationUserDto));
        assertThat(extractMessages(validationResults))
                .containsExactlyInAnyOrder(BLANK_FIELD_ERROR_MESSAGE);
    }

    @Test
    public void shouldNotValidateRegisterDtoWithMismatchPasswordsAndWrongEmailFormat() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto(
                LOGIN,
                VALID_PASSWORD,
                "WRONG_REPETED_PASSWORD",
                DISPLAY_NAME,
                "some-mail",
                ADMIN
        );

        addAll(validationResults, validator.validate(registrationUserDto));
        assertThat(extractMessages(validationResults))
                .containsExactlyInAnyOrder(PASSWORDS_MISMATCH_ERROR_MESSAGE, MAIL_VALIDATION_ERROR_MESSAGE);
    }

    @Test
    public void shouldValidateUpdateDtoWithFullData() {
        UpdateUserDto updateUserDto = new UpdateUserDto(
                LOGIN,
                VALID_PASSWORD,
                VALID_PASSWORD,
                DISPLAY_NAME,
                VALID_EMAIL
        );

        addAll(validationResults, validator.validate(updateUserDto));
        assertThat(extractMessages(validationResults))
                .isEmpty();
    }

    @Test
    public void shoulValidateUpdateDtoWithBlankLoginAndEmail() {
        UpdateUserDto updateUserDto = new UpdateUserDto(
                "",
                VALID_PASSWORD,
                VALID_PASSWORD,
                DISPLAY_NAME,
                ""
        );

        addAll(validationResults, validator.validate(updateUserDto));
        assertThat(extractMessages(validationResults))
                .isEmpty();
    }

    @Test
    public void shouldNotValidateUserDtoWithMismatchPasswords() {
        UpdateUserDto updateUserDto = new UpdateUserDto(
                LOGIN,
                VALID_PASSWORD,
                "other-password",
                DISPLAY_NAME,
                VALID_EMAIL
        );

        addAll(validationResults, validator.validate(updateUserDto));
        assertThat(extractMessages(validationResults))
                .containsExactlyInAnyOrder(PASSWORDS_MISMATCH_ERROR_MESSAGE);
    }

    @Test
    public void shouldNotValidateUpdateDtosWithWrongMailFormat() {
        UpdateUserDto updateUserDto = new UpdateUserDto(
                LOGIN,
                null,
                null,
                DISPLAY_NAME,
                "wrong-mail"
        );

        addAll(validationResults, validator.validate(updateUserDto));
        assertThat(extractMessages(validationResults))
                .containsExactlyInAnyOrder(MAIL_VALIDATION_ERROR_MESSAGE);
    }
}