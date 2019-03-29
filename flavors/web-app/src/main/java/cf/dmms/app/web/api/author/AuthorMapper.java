package cf.dmms.app.web.api.author;

import cf.dmms.app.core.author.Author;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class AuthorMapper {

	static Author toEntity(AuthorDto authorDto) {
		return new Author(
				authorDto.getName(),
				authorDto.getSurname()
		);
	}

	static AuthorDto toDto(Author author) {
		return new AuthorDto(
				author.getId(),
				author.getName(),
				author.getSurname()
		);
	}

	public static List<AuthorDto> toDto(Set<Author> authors) {
		return authors.stream()
				.map(AuthorMapper::toDto)
				.collect(toList());
	}
}
