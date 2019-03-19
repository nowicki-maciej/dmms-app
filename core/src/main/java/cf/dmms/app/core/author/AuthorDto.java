package cf.dmms.app.core.author;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AuthorDto {

	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@JsonCreator
	public AuthorDto(@JsonProperty("name") String name, @JsonProperty("surname") String surname) {
		this.name = name;
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
}
