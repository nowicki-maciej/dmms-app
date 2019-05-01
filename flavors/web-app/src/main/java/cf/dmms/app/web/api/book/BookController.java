package cf.dmms.app.web.api.book;

import cf.dmms.app.core.book.BookService;
import cf.dmms.app.spi.Book;
import cf.dmms.app.spi.MediaType;
import cf.dmms.app.web.resolver.CurrentUserId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	public List<BookDto> getAllBooks(@CurrentUserId Long userId) {
		List<Book> allBooks = bookService.getAllBooks(userId);
		return bookMapper.toDto(allBooks);
	}

	@GetMapping("/{bookId}")
	public BookDto getBook(@CurrentUserId Long userId, @PathVariable Long bookId) {
		Book book = bookService.getBook(userId, bookId);
		return bookMapper.toDto(book);
	}

	@DeleteMapping("/{bookId}")
	public void deleteBook(@CurrentUserId Long userId, @PathVariable Long bookId) {
		bookService.deleteBookById(userId, bookId);
	}

	@GetMapping("/{bookId}/{type}")
	ResponseEntity downloadBook(@CurrentUserId Long userId, @PathVariable Long bookId, @PathVariable MediaType type) {
		Book book = bookService.getBook(userId, bookId);

		InputStream bookContent = bookService.getBookContent(book, type);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "inline;filename=" + buildFilename(book, type));
		InputStreamResource resource = new InputStreamResource(bookContent);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	private String buildFilename(Book book, MediaType type) {
		try {
			return URLEncoder.encode(book.getTitle(), "UTF-8") + "." + type.getSaveExtension();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

}
