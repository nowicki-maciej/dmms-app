package cf.dmms.app.web.api.sharing;

import cf.dmms.app.web.api.book.BookDto;

public class SharedOutResourceDto {

	private String receiver;

	//Server destination

	private BookDto book;

	public SharedOutResourceDto(String receiver, BookDto book) {
		this.receiver = receiver;
		this.book = book;
	}

	public String getReceiver() {
		return receiver;
	}

	public BookDto getBook() {
		return book;
	}
}
