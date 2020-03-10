package com.starbux.web.controller.impl;

import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.web.controller.IToppingController;
import com.starbux.web.service.IToppingService;

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
@RequestMapping("/v1/topping")
@Tag(description = "Topping CRUD Operation", name = "Topping C/R/U/D")
public class ToppingControllerImpl implements IToppingController<ToppingReqDto, ToppingResDto> {

	@Autowired
	private IToppingService<ToppingReqDto, ToppingResDto> toppingService;

	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create a new topping")
	public ResponseEntity<ToppingResDto> create(@RequestBody ToppingReqDto toppingReqDto) {
		return ResponseEntity.ok(toppingService.create(toppingReqDto));
	}

	@Override
	@PatchMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update topping")
	public ResponseEntity<ToppingResDto> update(@RequestBody ToppingReqDto toppingReqDto) {
		return ResponseEntity.ok(toppingService.update(toppingReqDto));
	}

	@Override
	@Operation(summary = "Get topping by id")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ToppingResDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(toppingService.get(id));
	}

	@Override
	@Operation(summary = "Get all toppings")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ToppingResDto>> getAll() {
		return ResponseEntity.ok(toppingService.getAll());
	}

	@Override
	@PatchMapping(value = "/enable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Enable products")
	public ResponseEntity<ToppingResDto> enable(@PathVariable("id") Long id) {
		return ResponseEntity.ok(toppingService.enable(id));
	}

	@Override
	@PatchMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Disable products")
	public ResponseEntity<ToppingResDto> disable(@PathVariable("id") Long id) {
		return ResponseEntity.ok(toppingService.disable(id));
	}
}
