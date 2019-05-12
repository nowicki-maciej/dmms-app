package cf.dmms.app.web.api.book;

import cf.dmms.app.core.book.BookService;
import cf.dmms.app.spi.book.Book;
import cf.dmms.app.spi.book.MediaType;
import cf.dmms.app.web.resolver.CurrentUserId;
import cf.dmms.app.web.utils.HttpHeadersBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

	@PutMapping("/{bookId}")
	public BookDto updateBook(
			@CurrentUserId Long userId,
			@PathVariable Long bookId,
			@RequestBody NewBookDto bookDto) {
		Book book = bookMapper.toEntity(userId, bookDto);

		Book updatedBook = bookService.updateBook(userId, bookId, book);
		return bookMapper.toDto(updatedBook);
	}

	@GetMapping("/{bookId}/{type}")
	ResponseEntity downloadBook(
			HttpServletRequest request,
			@PathVariable Long bookId,
			@PathVariable MediaType type) {
		Book book = bookService.getBookUnsecured(bookId);

		HttpHeaders headers = HttpHeadersBuilder.responseFor(request)
				.contentType("application/octet-stream")
				.attachment(filename(book, type))
				.build();

		InputStream bookContent = bookService.getBookContent(book, type);
		InputStreamResource resource = new InputStreamResource(bookContent);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	private String filename(Book book, MediaType type) {
		return book.getTitle() + "." + type.getSaveExtension();
	}
}
