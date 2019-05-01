package cf.dmms.app.core.book.storage;

import cf.dmms.app.core.author.AuthorRepository;
import cf.dmms.app.core.book.BookRepository;
import cf.dmms.app.core.book.BookService;
import cf.dmms.app.core.book.IntegrationTestBase;
import cf.dmms.app.core.book.category.CategoryRepository;
import cf.dmms.app.spi.*;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = BookStorageConfig.class)
@RunWith(SpringRunner.class)
public class BookStorageIntegrationTest extends IntegrationTestBase {

	private static final String PDF_STORAGE_TOKEN = "1234";
	private static final String TXT_STORAGE_TOKEN = "4321";
	private static final byte[] PDF_CONTENT = new byte[] { 1, 2, 3 };
	private static final byte[] TXT_CONTENT = new byte[] { 3, 2, 1 };

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookContentStore bookContentStore;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Before
	public void setup() {
		bookRepository.deleteAll();
		categoryRepository.deleteAll();
		authorRepository.deleteAll();
	}

	@AfterClass
	public static void cleanup() throws IOException {
		Files.walk(Paths.get("/tmp/books"))
				.forEach(path -> {
					try {
						Files.delete(path);
					} catch (IOException e) {
					}
				});
	}

	@Test
	@Transactional
	public void properlySavesBook() throws IOException {
		Book book = persistedBookWithNoFormats();
		Format format = new Format(PDF_STORAGE_TOKEN, MediaType.PDF);

		bookService.addBookFormat(book.getId(), format, new ByteArrayInputStream(PDF_CONTENT));

		InputStream is = bookContentStore.getContent(format);
		byte[] content = IOUtils.toByteArray(is);
		assertThat(content)
				.isNotNull();
		assertThat(content)
				.containsExactly(PDF_CONTENT);
	}

	@Test
	@Transactional
	public void shouldProperlySaveBookInMultipleFormats() {
		Book book = persistedBookWithNoFormats();
		Format pdfFormat = new Format(PDF_STORAGE_TOKEN, MediaType.PDF);
		Format txtFormat = new Format(TXT_STORAGE_TOKEN, MediaType.TXT);

		bookService.addBookFormat(book.getId(), pdfFormat, new ByteArrayInputStream(PDF_CONTENT));
		bookService.addBookFormat(book.getId(), txtFormat, new ByteArrayInputStream(TXT_CONTENT));

		assertThat(Files.exists(Paths.get("/tmp/books/PDF/1234.pdf")))
				.isTrue();

		assertThat(Files.exists(Paths.get("/tmp/books/TXT/4321.txt")))
				.isTrue();

		assertThat(bookRepository.getOne(book.getId()).getFormats())
				.hasSize(2);
	}

	private Book persistedBookWithNoFormats() {
		Set<Format> formats = new HashSet<>();

		Set<Author> authors = new HashSet<>();
		authors.add(new Author("Jan", "Kowalski"));

		Set<Category> categories = new HashSet<>();
		categories.add(new Category("SciFi"));

		Book book = new Book(
				null,
				"title",
				"isbn",
				"desc",
				formats,
				authors,
				categories
		);

		return bookRepository.save(book);
	}
}
