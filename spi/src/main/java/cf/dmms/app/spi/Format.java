package cf.dmms.app.spi;

import org.springframework.content.commons.annotations.ContentId;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "formats")
public class Format {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@ContentId
	private String storageToken;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MediaType format;

	Format() {
	}

	public Format(String storageToken, MediaType format) {
		this.storageToken = storageToken;
		this.format = format;
	}

	public static Format withUUIDToken(MediaType mediaType) {
		return new Format(
				UUID.randomUUID().toString(),
				mediaType
		);
	}

	public Long getId() {
		return id;
	}

	public String getStorageToken() {
		return storageToken;
	}

	public MediaType getFormat() {
		return format;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Format other = (Format) o;
		return Objects.equals(format, other.format);
	}

	@Override
	public int hashCode() {
		return Objects.hash(format);
	}
}
