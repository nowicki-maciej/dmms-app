package cf.dmms.app.web.api.book;

import cf.dmms.app.core.book.Book;
import cf.dmms.app.core.book.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
class BookController {

	private BookService bookService;
	private BookMapper bookMapper;

	public BookController(BookService bookService, BookMapper bookMapper) {
		this.bookService = bookService;
		this.bookMapper = bookMapper;
	}

	@GetMapping
	public List<BookDto> getAllBooks() {
		return bookMapper.toDto(bookService.getAllBooks());
	}

	@GetMapping("/{bookId}")
	public BookDto getBooks(@PathVariable Long bookId) {
		return bookMapper.toDto(bookService.getBook(bookId));
	}

	@PostMapping
	public BookDto addBook(@Valid @RequestBody NewBookDto newBookDto) {
		Book book = bookMapper.toEntity(newBookDto);
		return bookMapper.toDto(bookService.addBook(book));
	}

	@DeleteMapping("/{bookId}")
	public void deleteBook(@PathVariable Long bookId) {
		bookService.deleteBookById(bookId);
	}
}
