package cf.dmms.app.web.api.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

	private Long id;

	@NotBlank
	private String name;

	@JsonCreator
	public CategoryDto(@JsonProperty("name") String name) {
		this.name = name;
	}

	public CategoryDto(Long id, String name) {
		this.id = id;
		this.name = name;
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
}
