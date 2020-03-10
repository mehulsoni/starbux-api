package com.starbux.web;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IController<T, U> {
	ResponseEntity<U> create(T t);

	ResponseEntity<U> update(T t);

	ResponseEntity<U> get(Long id);

	ResponseEntity<List<U>> getAll();

	ResponseEntity<U> enable(Long id);

	ResponseEntity<U> disable(Long id);
}
