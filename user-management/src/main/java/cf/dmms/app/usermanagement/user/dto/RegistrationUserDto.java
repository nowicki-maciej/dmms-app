package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class RegistrationUserDto {

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String repeatedPassword;

    private String displayName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Role role;

    @JsonCreator
    public RegistrationUserDto(
            @JsonProperty("login") @NotNull String login,
            @JsonProperty("password") @NotNull String password,
            @JsonProperty("repeatedPassword") @NotNull String repeatedPassword,
            @JsonProperty("displayName") String displayName,
            @JsonProperty("email") @NotNull @Email String email,
            @JsonProperty("role") @NotNull Role role) {
        this.login = login;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
