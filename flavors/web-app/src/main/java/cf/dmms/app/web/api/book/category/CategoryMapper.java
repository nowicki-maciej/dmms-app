package cf.dmms.app.web.api.book.category;

import cf.dmms.app.core.book.category.Category;

class CategoryMapper {

	static Category toEntity(CategoryDto categoryDto) {
		return new Category(categoryDto.getName());
	}
}
