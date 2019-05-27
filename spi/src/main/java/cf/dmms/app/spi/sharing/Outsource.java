package cf.dmms.app.spi.sharing;

import cf.dmms.app.spi.server.Server;
import cf.dmms.app.spi.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

@Entity(name = "outsources")
public class Outsource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private User owner;

	@Column(nullable = false)
	private String receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "server_id")
	private Server destination;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "outsource")
	private List<OutResource> sharedResources;

	@Deprecated
	Outsource() {
	}

	public Outsource(User owner, String receiver) {
		this.owner = owner;
		this.receiver = receiver;
		this.sharedResources = emptyList();
	}

	public Outsource(User owner, String receiver, Server destination) {
		this.owner = owner;
		this.receiver = receiver;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public String getReceiver() {
		return receiver;
	}

	public List<OutResource> getSharedResources() {
		return sharedResources;
	}

	public void setSharedResources(List<OutResource> sharedResources) {
		this.sharedResources = sharedResources;
	}

	public Optional<Server> getDestination() {
		return Optional.ofNullable(destination);
	}

	public String getDestinationServerIp() {
		if (nonNull(destination)) {
			return destination.getIpAddress();
		}
		return "Local";
	}
}
