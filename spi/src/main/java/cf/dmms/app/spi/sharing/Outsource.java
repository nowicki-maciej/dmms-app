package cf.dmms.app.spi.sharing;

import cf.dmms.app.spi.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

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

	//Server destination

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
}
