package cf.dmms.app.web.api.book.category;

import cf.dmms.app.core.book.category.Category;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CategoryMapper {

	static Category toEntity(CategoryDto categoryDto) {
		return new Category(categoryDto.getName());
	}

	static CategoryDto toDto(Category category) {
		return new CategoryDto(
				category.getId(),
				category.getName()
		);
	}

	public static List<CategoryDto> toDto(Collection<Category> categories) {
		return categories.stream()
				.map(CategoryMapper::toDto)
				.collect(toList());
	}
}
