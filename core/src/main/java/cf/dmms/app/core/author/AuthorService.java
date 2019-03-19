package cf.dmms.app.core.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	private AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public Author getAuthorById(Long id) {
		return authorRepository.getOne(id);
	}

	public Author createAuthor(AuthorDto authorDto) {
		Author author = new Author(authorDto.getName(), authorDto.getSurname());
		return authorRepository.save(author);
	}

	public Author updateAuthor(AuthorDto authorDto) {
		Author author = authorRepository.getOne(authorDto.getId());
		author.setName(authorDto.getName());
		author.setSurname(authorDto.getSurname());

		return author;
	}

	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}
}
