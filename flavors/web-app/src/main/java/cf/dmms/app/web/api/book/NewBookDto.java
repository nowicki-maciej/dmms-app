package cf.dmms.app.web.api.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

// TODO: 3/29/19 include file (formats) uploading
class NewBookDto {

	@NotBlank
	private String title;

	private String isbn;

	private String description;

	@NotEmpty
	private List<Long> authors;

	@NotEmpty
	private List<Long> categories;

	@JsonCreator
	public NewBookDto(
			@JsonProperty("title") String title,
			@JsonProperty("isbn") String isbn,
			@JsonProperty("description") String description,
			@JsonProperty("authors") List<Long> authors,
			@JsonProperty("categories") List<Long> categories) {
		this.title = title;
		this.isbn = isbn;
		this.description = description;
		this.authors = authors;
		this.categories = categories;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getDescription() {
		return description;
	}

	public List<Long> getAuthors() {
		return authors;
	}

	public List<Long> getCategories() {
		return categories;
	}
}
