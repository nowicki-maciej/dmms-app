package cf.dmms.app.usermanagement.user.dto.validation;

import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class UpdateDtoPasswordsValidator implements ConstraintValidator<ValidPasswords, UpdateUserDto> {

	@Override
	public void initialize(ValidPasswords constraint) {
	}

	@Override
	public boolean isValid(UpdateUserDto registerDto, ConstraintValidatorContext context) {
		return isNull(registerDto.getPassword()) || registerDto.getPassword().equals(registerDto.getRepeatedPassword());
	}

}