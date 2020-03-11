package com.starbux.web.service.impl;

import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.entity.Product;
import com.starbux.web.mapper.IProductMapper;
import com.starbux.web.repository.IProductRepository;
import com.starbux.web.service.IProductService;

import org.apache.commons.collections4.CollectionUtils;
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
	public Optional<ProductResDto> create(ProductReqDto productReqDto) {
		Product product = transformToEntity(productReqDto);
		return Optional.of(transformToResponse(productRepository.save(product)));
	}

	@Override
	public Optional<ProductResDto> update(ProductReqDto productReqDto) {
		Product product = transformToEntity(productReqDto);
		return Optional.of(transformToResponse(productRepository.save(product)));
	}

	@Override
	public Optional<ProductResDto> get(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return Optional.of(transformToResponse(product.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<ProductResDto>> getAll() {
		List<Product> products = productRepository.findAll();
		if (CollectionUtils.isNotEmpty(products)) {
			return Optional.of(transformToResponse(products));
		}
		return Optional.empty();
	}

	@Override
	public Optional<ProductResDto> enable(Long id) {
		return toggle(id, true);
	}

	@Override
	public Optional<ProductResDto> disable(Long id) {
		return toggle(id, false);
	}

	private Optional<ProductResDto> toggle(Long id, boolean b) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setEnabled(b);
			product = productRepository.save(product);
			return Optional.ofNullable(transformToResponse(product));
		}
		return Optional.empty();
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
