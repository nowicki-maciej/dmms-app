package cf.dmms.app.core.book.category;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map(Category::from)
				.collect(toList());
	}

	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = new Category(categoryDto.getName());
		category = categoryRepository.save(category);
		categoryDto.setId(category.getId());

		return categoryDto;
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
}
