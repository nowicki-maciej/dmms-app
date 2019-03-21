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

	private AuthorDto(Long id, @NotBlank String name, @NotBlank String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	@JsonCreator
	public AuthorDto(@JsonProperty("name") String name, @JsonProperty("surname") String surname) {
		this.name = name;
		this.surname = surname;
	}

	public static AuthorDto from(Author author) {
		return new AuthorDto(
				author.getId(),
				author.getName(),
				author.getSurname()
		);
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
