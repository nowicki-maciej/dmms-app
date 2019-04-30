package cf.dmms.app.core.book;

import cf.dmms.app.core.book.storage.BookContentStore;
import cf.dmms.app.spi.Book;
import cf.dmms.app.spi.BookFormatAlreadyExistsException;
import cf.dmms.app.spi.Format;
import cf.dmms.app.spi.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BookService {

	private static final Logger log = LoggerFactory.getLogger(BookService.class);

	private BookRepository bookRepository;
	private BookContentStore bookContentStore;

	public BookService(BookRepository bookRepository, BookContentStore bookContentStore) {
		this.bookRepository = bookRepository;
		this.bookContentStore = bookContentStore;
	}

	public List<Book> getAllBooks(Long userId) {
		return bookRepository.findAllByUserId(userId);
	}

	public Book getBook(Long userId, Long bookId) {
		return bookRepository.getOneByIdAndUserId(bookId, userId);
	}

	public void deleteBookById(Long userId, Long bookId) {
		bookRepository.deleteByIdAndUserId(bookId, userId);
	}

	public Book addBook(Book book, Map<MediaType, byte[]> bookFiles) {
		if (bookFiles.isEmpty()) {
			throw new IllegalStateException("Book should have at least one format.");
		}
		log.info("Adding book. Submitted book formats: {}", bookFiles.keySet());

		bookFiles.forEach((type, content) -> {
			Format format = Format.withUUIDToken(type);
			bookContentStore.setContent(format, new ByteArrayInputStream(content));
			book.getFormats().add(format);
			log.info("Adding format {} with storage token {}", format.getFormat(), format.getStorageToken());
		});

		return bookRepository.save(book);
	}

	public InputStream getBookContent(Book book, MediaType type) {
		Format format = book.getFormats().stream()
				.filter(f -> f.getFormat() == type)
				.findFirst()
				.orElseThrow(EntityNotFoundException::new);

		return bookContentStore.getContent(format);
	}

	public void addBookFormat(Long bookId, Format format, InputStream inputStream) {
		Book book = bookRepository.getOne(bookId);
		if (book.getFormats().contains(format)) {
			throw new BookFormatAlreadyExistsException();
		}

		bookContentStore.setContent(format, inputStream);
		book.getFormats().add(format);
		log.info("Adding book format {} with storage token {}", format.getFormat(), format.getStorageToken());
	}
}
