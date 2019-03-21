package cf.dmms.app.usermanagement.user.dto;


import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicUserDto {

    private Long id;
    private String login;
    private String displayName;
    private Role role;

    @JsonCreator
    public BasicUserDto(
            @JsonProperty("id") Long id,
            @JsonProperty("login") String login,
            @JsonProperty("diplayName") String displayName,
            @JsonProperty("role") Role role) {
        this.id = id;
        this.login = login;
        this.displayName = displayName;
        this.role = role;
    }
}
