package com.starbux.web.service.impl;

import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.entity.Product;
import com.starbux.exception.NotFoundException;
import com.starbux.web.mapper.IProductMapper;
import com.starbux.web.repository.IProductRepository;
import com.starbux.web.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService<ProductReqDto, ProductResDto> {

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IProductMapper productMapper;

	@Override
	public ProductResDto create(ProductReqDto productReqDto) {
		Product product = transformToEntity(productReqDto);
		return transformToResponse(productRepository.save(product));
	}

	@Override
	public ProductResDto update(ProductReqDto productReqDto) {
		Product product = transformToEntity(productReqDto);
		return transformToResponse(productRepository.save(product));
	}

	@Override
	public ProductResDto get(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return transformToResponse(product.get());
		}
		throw new NotFoundException(
				String.format("Product not found for given id {}", id));
	}

	@Override
	public List<ProductResDto> getAll() {
		return transformToResponse(productRepository.findAll());
	}

	@Override
	public ProductResDto enable(Long id) {
		return toggle(id, true);
	}

	@Override
	public ProductResDto disable(Long id) {
		return toggle(id, false);
	}

	private ProductResDto toggle(Long id, boolean b) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setEnabled(b);
			product = productRepository.save(product);
			return transformToResponse(product);
		}
		throw new NotFoundException(
				String.format("Product not found for given id {}", id));
	}

	private ProductResDto transformToResponse(Product product) {
		return productMapper.entityToResponseDto(product);
	}

	private Product transformToEntity(ProductReqDto productReqDto) {
		return productMapper.requestDtoToEntity(productReqDto);
	}

	private List<ProductResDto> transformToResponse(List<Product> products) {
		return productMapper.entitiesToResponseDtoList(products);
	}
}
