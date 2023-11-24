package br.com.guifroes1984.ecom.services.admin.category;

import java.util.List;

import br.com.guifroes1984.ecom.dto.CategoryDto;
import br.com.guifroes1984.ecom.entity.Category;

public interface CategoryService {

	Category createCategory(CategoryDto categoryDto);
	
	List<Category> getAllCategories();
	
}
