package cf.dmms.app.web.api.sharing;

import cf.dmms.app.web.api.book.BookDto;

public class SharedResourceDto {

	private String owner;

	//Server source

	private BookDto book;

	public SharedResourceDto(String owner, BookDto book) {
		this.owner = owner;
		this.book = book;
	}

	public String getOwner() {
		return owner;
	}

	public BookDto getBook() {
		return book;
	}
}
