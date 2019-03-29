package cf.dmms.app.core.book;

import javax.persistence.*;

@Entity
@Table(name = "formats")
public class Format {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String storageToken;

	@Enumerated(EnumType.STRING)
	private Format format;

	Format() {
	}

	public Format(String storageToken, Format format) {
		this.storageToken = storageToken;
		this.format = format;
	}

	public Long getId() {
		return id;
	}

	public String getStorageToken() {
		return storageToken;
	}

	public Format getFormat() {
		return format;
	}
}
