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
	public Author getAuthor(@PathVariable Long authorId) {
		return authorService.getAuthorById(authorId);
	}

	@PostMapping
	public Author createAuthor(@RequestBody @Valid AuthorDto authorDto) {
		return authorService.createAuthor(authorDto);
	}

	@PutMapping
	public Author updateAuthor(@RequestBody AuthorDto authorDto) {
		return authorService.updateAuthor(authorDto);
	}

	@DeleteMapping("/{authorId}")
	public void deleteAuthor(@PathVariable Long authorId) {
		authorService.deleteAuthor(authorId);
	}
}
