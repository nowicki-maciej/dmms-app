package cf.dmms.app.web.api.author;

import cf.dmms.app.core.author.AuthorService;
import cf.dmms.app.spi.Author;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/authors")
class AuthorController {

	private AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping
	public List<AuthorDto> getAllAuthors() {
		return authorService.getAllAuthors().stream()
				.map(AuthorMapper::toDto)
				.collect(toList());
	}

	@GetMapping("/{authorId}")
	public AuthorDto getAuthor(@PathVariable Long authorId) {
		Author author = authorService.getAuthorById(authorId);
		return AuthorMapper.toDto(author);
	}

	@PostMapping
	public AuthorDto createAuthor(@RequestBody @Valid AuthorDto authorDto) {
		Author author = AuthorMapper.toEntity(authorDto);
		return AuthorMapper.toDto(authorService.createAuthor(author));
	}

	// TODO: 5/1/19 Decide how should it be implemented
//	@PutMapping("/{authorId}")
//	public AuthorDto updateAuthor(@PathVariable Long authorId, @RequestBody @Valid AuthorDto authorDto) {
//		Author author = AuthorMapper.toEntity(authorDto);
//		return AuthorMapper.toDto(authorService.updateAuthor(authorId, author));
//	}

	// TODO: 5/1/19 Decide how should it be implemented
//	@DeleteMapping("/{authorId}")
//	public void deleteAuthor(@PathVariable Long authorId) {
//		authorService.deleteAuthor(authorId);
//	}
}
