package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegistrationUserDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatedPassword;

    private String displayName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Role role;

    @AssertTrue(message = "passwords must be the same!")
    private boolean arePasswordsValid;

    @JsonCreator
    public RegistrationUserDto(
            @JsonProperty("login") @NotBlank String login,
            @JsonProperty("password") @NotBlank String password,
            @JsonProperty("repeatedPassword") @NotBlank String repeatedPassword,
            @JsonProperty("displayName") String displayName,
            @JsonProperty("email") @NotBlank @Email String email,
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

    private boolean arePasswordsValid() {
        return password.equals(repeatedPassword);
    }
}
