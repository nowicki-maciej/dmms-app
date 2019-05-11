package cf.dmms.app.web.api.sharing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SharingRequest {

	@NotEmpty
	private String receiver;

	@NotNull
	private List<Long> booksId;

	@JsonCreator
	public SharingRequest(
			@JsonProperty("receiver") String receiver,
			@JsonProperty("booksId") List<Long> booksId) {
		this.receiver = receiver;
		this.booksId = booksId;
	}

	public String getReceiver() {
		return receiver;
	}

	public List<Long> getBooksId() {
		return booksId;
	}
}
