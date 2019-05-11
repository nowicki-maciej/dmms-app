package cf.dmms.app.web.api.book;

import cf.dmms.app.core.author.AuthorService;
import cf.dmms.app.core.book.category.CategoryService;
import cf.dmms.app.spi.Author;
import cf.dmms.app.spi.book.Book;
import cf.dmms.app.spi.Category;
import cf.dmms.app.usermanagement.user.UserRepository;
import cf.dmms.app.web.api.author.AuthorMapper;
import cf.dmms.app.web.api.category.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class BookMapper {

	private AuthorService authorService;
	private CategoryService categoryService;
	private UserRepository userRepository;

	BookMapper(AuthorService authorService, CategoryService categoryService, UserRepository userRepository) {
		this.authorService = authorService;
		this.categoryService = categoryService;
		this.userRepository = userRepository;
	}

	Book toEntity(Long userId, NewBookDto newBookDto) {
		Set<Author> authors = new HashSet<>(authorService.getAllAuthors(newBookDto.getAuthors()));
		Set<Category> categories = new HashSet<>(categoryService.getAllCategories(newBookDto.getCategories()));

		return new Book(
				userRepository.getOne(userId),
				newBookDto.getTitle(),
				newBookDto.getIsbn(),
				newBookDto.getDescription(),
				new HashSet<>(),
				authors,
				categories
		);
	}

	public BookDto toDto(Book book) {
		return new BookDto(
				book.getId(),
				book.getTitle(),
				book.getIsbn(),
				book.getDescription(),
				AuthorMapper.toDto(book.getAuthors()),
				CategoryMapper.toDto(book.getCategories()),
				formatsToStringList(book)
		);
	}

	private List<String> formatsToStringList(Book book) {
		return book.getFormats().stream().map(format -> format.getFormat().toString()).collect(toList());
	}

	List<BookDto> toDto(List<Book> books) {
		return books.stream()
				.map(this::toDto)
				.collect(toList());
	}
}
