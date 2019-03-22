package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.dto.BasicUserDto;
import cf.dmms.app.usermanagement.user.dto.RegistrationUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static BasicUserDto toDto(User user) {
        return new BasicUserDto(
                user.getId(),
                user.getLogin(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User toEntity(RegistrationUserDto userDto, PasswordEncoder encoder) {
        return new User(
                userDto.getLogin(),
                encoder.encode(userDto.getPassword()),
                userDto.getDisplayName(),
                userDto.getEmail(),
                userDto.getRole()
        );
    }
}
