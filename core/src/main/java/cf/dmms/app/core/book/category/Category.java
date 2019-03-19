package cf.dmms.app.core.book.category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String name;

	Category() {
	}

	public Category(String name) {
		this.name = name;
	}


	public static CategoryDto from(Category category) {
		return new CategoryDto(category.getId(), category.getName());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
