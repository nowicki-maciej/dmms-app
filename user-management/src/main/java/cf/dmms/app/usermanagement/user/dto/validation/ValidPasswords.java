package cf.dmms.app.usermanagement.user.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { RegistrationDtoPasswordsValidator.class, UpdateDtoPasswordsValidator.class })
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswords {
	String message() default "passwords must be the same";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
