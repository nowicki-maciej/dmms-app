package cf.dmms.app.web.api.book;

import cf.dmms.app.web.api.author.AuthorDto;
import cf.dmms.app.web.api.category.CategoryDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookDto {

	private Long id;
	private String title;
	private String isbn;
	private String description;
	private List<AuthorDto> authors;
	private List<CategoryDto> categories;
	private List<String> formats;

	@JsonCreator
	BookDto(
			@JsonProperty("id") Long id,
			@JsonProperty("title") String title,
			@JsonProperty("isbn") String isbn,
			@JsonProperty("description") String description,
			@JsonProperty("authors") List<AuthorDto> authors,
			@JsonProperty("categories") List<CategoryDto> categories,
			@JsonProperty("formats") List<String> formats) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.description = description;
		this.authors = authors;
		this.categories = categories;
		this.formats = formats;
	}

	public Long getId() {
		return id;
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

	public List<AuthorDto> getAuthors() {
		return authors;
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public List<String> getFormats() {
		return formats;
	}
}
