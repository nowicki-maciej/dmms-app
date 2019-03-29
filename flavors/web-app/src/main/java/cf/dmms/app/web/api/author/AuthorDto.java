package cf.dmms.app.web.api.author;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

class AuthorDto {

	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	AuthorDto(Long id, @NotBlank String name, @NotBlank String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	@JsonCreator
	AuthorDto(@JsonProperty("name") String name, @JsonProperty("surname") String surname) {
		this.name = name;
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
