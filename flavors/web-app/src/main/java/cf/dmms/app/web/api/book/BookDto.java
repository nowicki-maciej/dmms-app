package cf.dmms.app.web.api.book;

import java.util.List;

class BookDto {

	private Long id;
	private String title;
	private String isbn;
	private String description;
	private List<Long> authors;
	private List<Long> categories;

	public BookDto(Long id, String title, String isbn, String description, List<Long> authors, List<Long> categories) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.description = description;
		this.authors = authors;
		this.categories = categories;
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

	public List<Long> getAuthors() {
		return authors;
	}

	public List<Long> getCategories() {
		return categories;
	}
}
