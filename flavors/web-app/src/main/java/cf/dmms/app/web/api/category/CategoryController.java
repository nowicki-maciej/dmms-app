package cf.dmms.app.web.api.category;

import cf.dmms.app.core.book.category.CategoryService;
import cf.dmms.app.spi.Category;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<Category> getCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping
	public Category createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		Category category = CategoryMapper.toEntity(categoryDto);

		return categoryService.createCategory(category);
	}

	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
	}
}
