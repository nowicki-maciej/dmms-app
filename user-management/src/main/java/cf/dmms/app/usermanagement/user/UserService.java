package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.api.exception.UserNotFoundException;
import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import cf.dmms.app.usermanagement.user.dto.RoleUpdateUserDto;
import cf.dmms.app.usermanagement.user.dto.UpdateUserDto;

import java.util.List;

public interface UserService {

	BasicUserDto findById(Long id) throws UserNotFoundException;

	List<BasicUserDto> findAllUsers();

	void updateCurrentUser(UpdateUserDto userDto) throws UserNotFoundException;

	void updateUserRole(RoleUpdateUserDto userDto) throws UserNotFoundException;

	void deleteUser(Long userId) throws UserNotFoundException;

	void register(RegistrationUserDto registrationUserDto);
}
