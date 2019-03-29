package cf.dmms.app.web.api.author;

import cf.dmms.app.core.author.Author;

class AuthorMapper {

//	static AuthorDto toDto(Author author) {
//		return AuthorDto.from(
//				author.getId(),
//				author.getName(),
//				author.getSurname()
//		);
//	}

	static Author toEntity(AuthorDto authorDto) {
		return new Author(
				authorDto.getName(),
				authorDto.getSurname()
		);
	}

	public static AuthorDto toDto(Author author) {
		return new AuthorDto(
				author.getId(),
				author.getName(),
				author.getSurname()
		);
	}
}
