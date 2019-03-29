package cf.dmms.app.core.book;

import cf.dmms.app.core.author.AuthorService;
import cf.dmms.app.core.book.category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

	private AuthorService authorService;
	private CategoryService categoryService;
	private BookRepository bookRepository;

	public BookService(AuthorService authorService, CategoryService categoryService, BookRepository bookRepository) {
		this.authorService = authorService;
		this.categoryService = categoryService;
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBook(Long id) {
		return bookRepository.getOne(id);
	}

	public void deleteBookById(Long id) {
		bookRepository.deleteById(id);
	}

	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

//	public Book addNewBook(NewBookDto newBookDto) {
//		Set<Author> authors = new HashSet<>(authorService.getAllAuthors(newBookDto.getAuthors()));
//		Set<Category> categories = new HashSet<>(categoryService.getAllCategories(newBookDto.getCategories()));
//		Book book = BookMapper.toEntity(newBookDto, authors, categories);
//		book = bookRepository.save(book);
//		return BookMapper.toDto(book);
//	}
}
