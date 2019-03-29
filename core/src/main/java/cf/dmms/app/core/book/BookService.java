package cf.dmms.app.core.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
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
}
