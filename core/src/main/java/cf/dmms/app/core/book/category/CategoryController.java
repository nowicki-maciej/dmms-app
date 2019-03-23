package cf.dmms.app.core.book.category;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<CategoryDto> getCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping
	public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		return categoryService.createCategory(categoryDto);
	}

	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
	}
}
