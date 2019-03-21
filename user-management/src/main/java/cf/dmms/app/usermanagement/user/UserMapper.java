package cf.dmms.app.usermanagement.user;

import cf.dmms.app.usermanagement.user.dto.BasicUserDto;

public class UserMapper {

    public static BasicUserDto mapToBasicUserDto(User user) {
        return new BasicUserDto(
                user.getId(),
                user.getLogin(),
                user.getDisplayName(),
                user.getRole()
        );
    }
}
