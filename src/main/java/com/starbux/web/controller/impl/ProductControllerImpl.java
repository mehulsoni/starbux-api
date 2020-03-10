package com.starbux.web.controller.impl;

import com.starbux.dto.request.ProductReqDto;
import com.starbux.dto.response.ProductResDto;
import com.starbux.web.controller.IProductController;
import com.starbux.web.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/product")
@Tag(description = "Product CRUD Operation", name = "Product C/R/U/D")
public class ProductControllerImpl implements IProductController<ProductReqDto, ProductResDto> {

	@Autowired
	private IProductService<ProductReqDto, ProductResDto> productService;

	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create a new product")
	public ResponseEntity<ProductResDto> create(@RequestBody ProductReqDto productReqDto) {
		return ResponseEntity.ok(productService.create(productReqDto));
	}

	@Override
	@PatchMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update product")
	public ResponseEntity<ProductResDto> update(@RequestBody ProductReqDto productReqDto) {
		return ResponseEntity.ok(productService.update(productReqDto));
	}

	@Override
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get product by id")
	public ResponseEntity<ProductResDto> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.get(id));
	}

	@Override
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all products")
	public ResponseEntity<List<ProductResDto>> getAll() {
		return ResponseEntity.ok(productService.getAll());
	}

	@Override
	@PatchMapping(value = "/enable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Enable products")
	public ResponseEntity<ProductResDto> enable(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.enable(id));
	}

	@Override
	@PatchMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Disable products")
	public ResponseEntity<ProductResDto> disable(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.disable(id));
	}
}
