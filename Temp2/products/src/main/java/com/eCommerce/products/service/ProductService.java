package com.eCommerce.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eCommerce.products.dto.ProductRequest;
import com.eCommerce.products.dto.ProductResponse;
import com.eCommerce.products.model.Product;
import com.eCommerce.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
		.name(productRequest.getName())
		.description(productRequest.getDescription())
		.price(productRequest.getPrice())
		.build();


		productRepository.save(product);

		log.info("Product {} is Saved", product.getId());
		
		
	}

	public List<ProductResponse> getAllProducts() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
		
		List<Product> products = productRepository.findAll();

		return products.stream().map(this::mapToProductResponse).toList();
		
	}

	private ProductResponse mapToProductResponse(Product product){

		return ProductResponse.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.price(product.getPrice())
			.build();

	}
}
