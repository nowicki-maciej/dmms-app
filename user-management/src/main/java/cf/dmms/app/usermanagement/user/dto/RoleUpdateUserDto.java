package cf.dmms.app.usermanagement.user.dto;

import cf.dmms.app.usermanagement.user.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoleUpdateUserDto {

	@NotNull
	private Long id;

	@NonNull
	private Role role;

	@JsonCreator
	public RoleUpdateUserDto(
			@JsonProperty("id") @NotBlank Long id,
			@JsonProperty("role") @NonNull Role role) {
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public Role getRole() {
		return role;
	}
}
