package cf.dmms.app.spi.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DummyServerDto {

	private String ipAddress;
	private Long assignedId;
	private ServerType serverType;

	@JsonCreator
	public DummyServerDto(@JsonProperty("ipAddress") String ipAddress,
						  @JsonProperty("assignedId") Long assignedId,
						  @JsonProperty("serverType") ServerType serverType) {
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

	public Server toEntity() {
		return new Server(ipAddress, assignedId, serverType);
	}
}
