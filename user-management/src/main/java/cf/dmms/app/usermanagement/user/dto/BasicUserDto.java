package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicUserDto {

	private Long id;
	private String login;
	private String displayName;
	private String email;
	private Role role;

	@JsonCreator
	public BasicUserDto(
			@JsonProperty("id") Long id,
			@JsonProperty("login") String login,
			@JsonProperty("diplayName") String displayName,
			@JsonProperty("email") String email,
			@JsonProperty("role") Role role) {
		this.id = id;
		this.login = login;
		this.displayName = displayName;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
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
