package cf.dmms.app.core.book.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category createCategory(CategoryDto categoryDto) {
		Category category = new Category(categoryDto.getName());
		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
}
