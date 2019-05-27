package cf.dmms.app.web.api.sharing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SharingRequest {

	@NotEmpty
	private String receiver;

	@NotNull
	private List<Long> booksId;

	private Long receiverServer;

	@JsonCreator
	public SharingRequest(
			@JsonProperty("receiver") String receiver,
			@JsonProperty("booksId") List<Long> booksId,
			@JsonProperty("receiverServer") Long receiverServer) {
		this.receiver = receiver;
		this.booksId = booksId;
		this.receiverServer = receiverServer;
	}

	public String getReceiver() {
		return receiver;
	}

	public List<Long> getBooksId() {
		return booksId;
	}

	public Optional<Long> getReceiverServer() {
		return Optional.ofNullable(receiverServer);
	}
}
