package cf.dmms.app.spi.sharing;

import cf.dmms.app.spi.book.Book;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "out_resources")
public class OutResource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "resource_id", unique = true, nullable = false)
	private String resourceId;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@ManyToOne
	@JoinColumn(name = "outsource_id", nullable = false)
	private Outsource outsource;

	//LocalDateTime expiration

	@Deprecated
	OutResource() {
	}

	public OutResource(Book book, Outsource outsource) {
		this.resourceId = UUID.randomUUID().toString();
		this.book = book;
		this.outsource = outsource;
	}

	public Long getId() {
		return id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public Book getBook() {
		return book;
	}

	public Outsource getOutsource() {
		return outsource;
	}
}
