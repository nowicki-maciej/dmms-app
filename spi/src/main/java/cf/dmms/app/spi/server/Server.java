package cf.dmms.app.spi.server;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "servers")
public class Server {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "ip_address")
	private String ipAddress;

	@Column
	private String name;

	@Column(name = "assigned_id")
	private Long assignedId;

	@Column
	@Enumerated(EnumType.STRING)
	private ServerType type;

	@Column
	private String token;

	@Column
	private LocalDate expiration;

	public Server() {
	}

	public Server(String ipAddress, Long assignedId, ServerType type) {
		this.ipAddress = ipAddress;
		this.assignedId = assignedId;
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getName() {
		return name;
	}

	public Long getAssignedId() {
		return assignedId;
	}

	public ServerType getType() {
		return type;
	}

	public String getToken() {
		return token;
	}

	public LocalDate getExpiration() {
		return expiration;
	}
}
