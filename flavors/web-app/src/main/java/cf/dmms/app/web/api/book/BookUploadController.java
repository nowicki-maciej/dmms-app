package cf.dmms.app.web.api.book;

import cf.dmms.app.core.book.Book;
import cf.dmms.app.core.book.BookService;
import cf.dmms.app.core.book.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@RestController
public class BookUploadController {

	private BookService bookService;
	private BookMapper bookMapper;
	private ObjectMapper objectMapper;
	private Validator validator;

	public BookUploadController(BookService bookService, BookMapper bookMapper, ObjectMapper objectMapper, Validator validator) {
		this.bookService = bookService;
		this.bookMapper = bookMapper;
		this.objectMapper = objectMapper;
		this.validator = validator;
	}

	@PostMapping
	public BookDto addBook(
			@RequestParam("book") String newBookDtoString,
			@RequestParam("file[]") List<MultipartFile> files) throws IOException {

		NewBookDto newBookDto = objectMapper.readValue(newBookDtoString, NewBookDto.class);
		validate(newBookDto);

		Book book = bookMapper.toEntity(newBookDto);
		Map<MediaType, byte[]> contentsByTypes = parseContentFiles(files);

		book = bookService.addBook(book, contentsByTypes);
		return bookMapper.toDto(book);
	}

	private Map<MediaType, byte[]> parseContentFiles(List<MultipartFile> files) {
		Map<MediaType, byte[]> contents = new HashMap<>();

		for (MultipartFile file : files) {
			byte[] content = getFileContent(file);
			String extension = fetchFileExtension(file);
			MediaType mediaType = MediaType.fromExtension(extension);

			contents.put(mediaType, content);
		}

		return contents;
	}

	private void validate(NewBookDto newBookDto) {
		Set<ConstraintViolation<NewBookDto>> validationResult = validator.validate(newBookDto);
		if (!validationResult.isEmpty()) {
			throw new IllegalStateException();
		}
	}

	private byte[] getFileContent(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String fetchFileExtension(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (isNull(extension)) {
			throw new IllegalStateException("File has no extension provided. Unable to resolve file type.");
		}
		return extension;
	}
}
