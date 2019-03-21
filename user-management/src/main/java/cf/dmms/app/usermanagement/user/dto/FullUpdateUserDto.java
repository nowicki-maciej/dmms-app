package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class FullUpdateUserDto {


    @NonNull
    private Long id;

    private String password;
    private String repeatedPassword;
    private String displayName;
    private String email;
    private Role role;

    @JsonCreator
    public FullUpdateUserDto(
            @JsonProperty("id") @NonNull Long id,
            @JsonProperty("password") String password,
            @JsonProperty("repeatedPassword") String repeatedPassword,
            @JsonProperty("displayName") String displayName,
            @JsonProperty("email") String email,
            @JsonProperty("role") Role role) {
        this.id = id;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
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
