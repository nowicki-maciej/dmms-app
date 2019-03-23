package cf.dmms.app.usermanagement.user.dto;

import org.junit.Test;

import static cf.dmms.app.usermanagement.user.Role.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;


public class DtoValidationTest extends ValidationTestBase {

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

        assertThat(extractMessages(validator.validate(registrationUserDto)))
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

        assertThat(extractMessages(validator.validate(registrationUserDto)))
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

        assertThat(extractMessages(validator.validate(registrationUserDto)))
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

        assertThat(extractMessages(validator.validate(updateUserDto)))
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

        assertThat(extractMessages(validator.validate(updateUserDto)))
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

        assertThat(extractMessages(validator.validate(updateUserDto)))
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

        assertThat(extractMessages(validator.validate(updateUserDto)))
                .containsExactlyInAnyOrder(MAIL_VALIDATION_ERROR_MESSAGE);
    }
}