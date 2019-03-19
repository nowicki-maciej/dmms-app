package cf.dmms.app.usermanagement.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @JsonCreator
    public LoginDto(
            @JsonProperty("login") @NotBlank String login,
            @JsonProperty("password")@NotBlank String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
