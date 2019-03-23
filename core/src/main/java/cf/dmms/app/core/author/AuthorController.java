package cf.dmms.app.core.author;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/author")
public class AuthorController {

	private AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/{authorId}")
	public AuthorDto getAuthor(@PathVariable Long authorId) {
		return authorService.getAuthorById(authorId);
	}

	@PostMapping
	public AuthorDto createAuthor(@RequestBody @Valid AuthorDto authorDto) {
		return authorService.createAuthor(authorDto);
	}

	@PutMapping("/{authorId}")
	public AuthorDto updateAuthor(@PathVariable Long authorId, @RequestBody @Valid AuthorDto authorDto) {
		return authorService.updateAuthor(authorId, authorDto);
	}

	@DeleteMapping("/{authorId}")
	public void deleteAuthor(@PathVariable Long authorId) {
		authorService.deleteAuthor(authorId);
	}
}
