package cf.dmms.app.web.api.book;

import cf.dmms.app.core.book.Book;
import cf.dmms.app.core.book.BookService;
import cf.dmms.app.core.book.MediaType;
import cf.dmms.app.web.resolver.CurrentUserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/books")
class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	private BookService bookService;
	private BookMapper bookMapper;

	public BookController(BookService bookService, BookMapper bookMapper) {
		this.bookService = bookService;
		this.bookMapper = bookMapper;
	}

	@GetMapping
	public List<BookDto> getAllBooks(@CurrentUserId Long userId) {
		log.info("[@@@@@@@@] UserId = {}", userId);

		return bookMapper.toDto(bookService.getAllBooks());
	}

	@GetMapping("/{bookId}")
	public BookDto getBooks(@PathVariable Long bookId) {
		return bookMapper.toDto(bookService.getBook(bookId));
	}

	@DeleteMapping("/{bookId}")
	public void deleteBook(@PathVariable Long bookId) {
		bookService.deleteBookById(bookId);
	}

	@GetMapping("/{bookId}/{type}")
	ResponseEntity downloadBook(@PathVariable Long bookId, @PathVariable MediaType type) {
		Book book = bookService.getBook(bookId);
		InputStream bookContent = bookService.getBookContent(book, type);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "inline;filename=" + buildFilename(book, type));
		InputStreamResource resource = new InputStreamResource(bookContent);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	private String buildFilename(Book book, MediaType type) {
		return book.getTitle() + "." + type.getSaveExtension();
	}

}
