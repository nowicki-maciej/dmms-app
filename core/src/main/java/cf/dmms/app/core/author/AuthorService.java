package cf.dmms.app.core.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	private AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public AuthorDto getAuthorById(Long id) {
		Author author = authorRepository.getOne(id);
		return AuthorDto.from(author);
	}

	public AuthorDto createAuthor(AuthorDto authorDto) {
		Author author = new Author(authorDto.getName(), authorDto.getSurname());
		author = authorRepository.save(author);
		authorDto.setId(author.getId());

		return authorDto;
	}

	public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
		Author author = authorRepository.getOne(authorId);
		author.setName(authorDto.getName());
		author.setSurname(authorDto.getSurname());

		authorDto.setId(authorId);
		return authorDto;
	}

	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}
}
