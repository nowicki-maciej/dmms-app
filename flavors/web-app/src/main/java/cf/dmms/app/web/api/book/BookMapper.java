package cf.dmms.app.web.api.book;

import cf.dmms.app.core.author.Author;
import cf.dmms.app.core.author.AuthorService;
import cf.dmms.app.core.book.Book;
import cf.dmms.app.core.book.category.Category;
import cf.dmms.app.core.book.category.CategoryService;
import cf.dmms.app.web.api.author.AuthorMapper;
import cf.dmms.app.web.api.book.category.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
class BookMapper {

	private AuthorService authorService;
	private CategoryService categoryService;

	BookMapper(AuthorService authorService, CategoryService categoryService) {
		this.authorService = authorService;
		this.categoryService = categoryService;
	}

	Book toEntity(NewBookDto newBookDto) {
		Set<Author> authors = new HashSet<>(authorService.getAllAuthors(newBookDto.getAuthors()));
		Set<Category> categories = new HashSet<>(categoryService.getAllCategories(newBookDto.getCategories()));

		return new Book(
				newBookDto.getTitle(),
				newBookDto.getIsbn(),
				newBookDto.getDescription(),
				// TODO: 3/29/19
				Collections.emptySet(),
				authors,
				categories
		);
	}

	BookDto toDto(Book book) {
		return new BookDto(
				book.getId(),
				book.getTitle(),
				book.getIsbn(),
				book.getDescription(),
				AuthorMapper.toDto(book.getAuthors()),
				CategoryMapper.toDto(book.getCategories())
		);
	}

	List<BookDto> toDto(List<Book> books) {
		return books.stream()
				.map(this::toDto)
				.collect(toList());
	}
}
