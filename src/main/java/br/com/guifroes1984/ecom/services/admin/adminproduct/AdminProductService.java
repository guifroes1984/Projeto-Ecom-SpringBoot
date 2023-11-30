package br.com.guifroes1984.ecom.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;

import br.com.guifroes1984.ecom.dto.ProductDto;

public interface AdminProductService {

	
	ProductDto addProduct(ProductDto productDto) throws IOException;
	
	List<ProductDto> getAllProducts();
	
	List<ProductDto> getAllProductByName(String name);
	
	boolean deleteProduct(Long id);
}
