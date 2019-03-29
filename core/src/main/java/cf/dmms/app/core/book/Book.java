package cf.dmms.app.core.book;

import cf.dmms.app.core.author.Author;
import cf.dmms.app.core.book.category.Category;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String isbn;

	@Lob
	private String description;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Set<Format> formats;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "books_authors",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private Set<Author> authors;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "books_categories",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private Set<Category> categories;

	Book() {
	}

	public Book(
			String title,
			String isbn,
			String description,
			Set<Format> formats,
			Set<Author> authors,
			Set<Category> categories) {
		this.title = title;
		this.isbn = isbn;
		this.description = description;
		this.formats = formats;
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

	public Set<Format> getFormats() {
		return formats;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
