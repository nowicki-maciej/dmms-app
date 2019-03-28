package cf.dmms.app.usermanagement.user.dto.validation;

import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegistrationDtoPasswordsValidator implements ConstraintValidator<ValidPasswords, RegistrationUserDto> {

	@Override
	public void initialize(ValidPasswords constraint) {
	}

	@Override
	public boolean isValid(RegistrationUserDto registerDto, ConstraintValidatorContext context) {
		return registerDto.getPassword().equals(registerDto.getRepeatedPassword());
	}

}
