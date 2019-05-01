package cf.dmms.app.core.book.category;

import cf.dmms.app.spi.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAllCategories() {
		return new ArrayList<>(categoryRepository.findAll());
	}

	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	public List<Category> getAllCategories(List<Long> categoryIds) {
		return categoryRepository.findAllById(categoryIds);
	}
}
