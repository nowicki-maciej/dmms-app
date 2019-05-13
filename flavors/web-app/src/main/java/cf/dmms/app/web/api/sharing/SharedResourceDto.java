package cf.dmms.app.web.api.sharing;

import cf.dmms.app.web.api.book.BookDto;

public class SharedResourceDto {

	private String owner;

	//Server source

	private BookDto book;
	private String server;

	public SharedResourceDto(String owner, BookDto book, String server) {
		this.owner = owner;
		this.book = book;
		this.server = server;
	}

	public String getOwner() {
		return owner;
	}

	public BookDto getBook() {
		return book;
	}

	public String getServer() {
		return server;
	}
}
