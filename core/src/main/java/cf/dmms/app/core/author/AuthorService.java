package cf.dmms.app.core.author;

import cf.dmms.app.spi.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

	private AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public Author getAuthorById(Long id) {
		return authorRepository.getOne(id);
	}

	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}

	public Author updateAuthor(Long authorId, Author changedAuthor) {
		Author author = authorRepository.getOne(authorId);
		author.setName(changedAuthor.getName());
		author.setSurname(changedAuthor.getSurname());

		return author;
	}

	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}

	public List<Author> getAllAuthors(List<Long> authorIds) {
		return authorRepository.findAllById(authorIds);
	}

	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}
}
