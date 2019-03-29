package cf.dmms.app.core.book;

import org.springframework.content.commons.annotations.ContentId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "formats")
public class Format {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(nullable = false)
	@ContentId
	private String storageToken;

	@Enumerated(EnumType.STRING)
	private MediaType format;

	Format() {
	}

	public Format(String storageToken, MediaType format) {
		this.storageToken = storageToken;
		this.format = format;
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

	Book getBook() {
		return book;
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
