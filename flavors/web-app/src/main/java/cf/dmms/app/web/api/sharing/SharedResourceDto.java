package cf.dmms.app.web.api.sharing;

import cf.dmms.app.web.api.book.BookDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SharedResourceDto {

	private String owner;

	//Server source

	private BookDto book;
	private String server;

	@JsonCreator
	public SharedResourceDto(
			@JsonProperty("owner") String owner,
			@JsonProperty("book") BookDto book,
			@JsonProperty("server") String server) {
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
