package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.dto.validation.ValidPasswords;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;

@ValidPasswords
public class UpdateUserDto {

	private Long id;
	private String oldPassword;
	private String password;
	private String repeatedPassword;
	private String displayName;

	@Email
	private String email;

	@JsonCreator
	public UpdateUserDto(
			@JsonProperty("oldPassword") String oldPassword,
			@JsonProperty("password") String password,
			@JsonProperty("repeatedPassword") String repeatedPassword,
			@JsonProperty("displayName") String displayName,
			@JsonProperty("email") @Email String email) {
		this.oldPassword = oldPassword;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.displayName = displayName;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
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
}
