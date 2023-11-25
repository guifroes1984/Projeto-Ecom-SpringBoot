package br.com.guifroes1984.ecom.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.guifroes1984.ecom.dto.ProductDto;
import br.com.guifroes1984.ecom.entity.Category;
import br.com.guifroes1984.ecom.entity.Product;
import br.com.guifroes1984.ecom.repository.CategoryRepository;
import br.com.guifroes1984.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

	private final ProductRepository productRepository;
	
	private final CategoryRepository categoryRepository;
	
	public ProductDto addProduct(ProductDto productDto) throws IOException {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setImg(productDto.getImg().getBytes());
		
		Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
		
		product.setCategory(category);
		return productRepository.save(product).getDto();
	}
	
	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}
}
