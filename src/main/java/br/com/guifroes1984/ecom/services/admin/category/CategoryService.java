package br.com.guifroes1984.ecom.services.admin.category;

import br.com.guifroes1984.ecom.dto.CategoryDto;
import br.com.guifroes1984.ecom.entity.Category;

public interface CategoryService {

	Category createCategory(CategoryDto categoryDto);
	
}
