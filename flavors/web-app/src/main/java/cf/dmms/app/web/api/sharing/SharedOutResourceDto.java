package cf.dmms.app.web.api.sharing;

import cf.dmms.app.web.api.book.BookDto;

public class SharedOutResourceDto {

	private String receiver;

	//Server destination

	private BookDto book;

	private String server;

	public SharedOutResourceDto(String receiver, BookDto book, String server) {
		this.receiver = receiver;
		this.book = book;
		this.server = server;
	}

	public String getReceiver() {
		return receiver;
	}

	public BookDto getBook() {
		return book;
	}

	public String getServer() {
		return server;
	}
}
