package cf.dmms.app.spi.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DummyServerDto {

	private String ipAddress;
	private Long assignedId;
	private ServerType serverType;
	private boolean isAccepted;

	@JsonCreator
	public DummyServerDto(@JsonProperty("ipAddress") String ipAddress,
						  @JsonProperty("serverType") ServerType serverType) {
		this.ipAddress = ipAddress;
		this.serverType = serverType;
	}

	public DummyServerDto(String ipAddress, Long assignedId, ServerType serverType) {
		this.ipAddress = ipAddress;
		this.assignedId = assignedId;
		this.serverType = serverType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Long getAssignedId() {
		return assignedId;
	}

	public ServerType getServerType() {
		return serverType;
	}

	public void setAssignedId(Long assignedId) {
		this.assignedId = assignedId;
	}

	public Server toEntity() {
		return new Server(ipAddress, assignedId, serverType);
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean accepted) {
		isAccepted = accepted;
	}
}
