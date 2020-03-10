package com.starbux.web;

import java.util.List;
import java.util.Optional;

public interface IService<T, U> {
	Optional<U> create(T t);

	Optional<U> update(T t);

	Optional<U> get(Long id);

	Optional<List<U>> getAll();

	Optional<U> enable(Long id);

	Optional<U> disable(Long id);
}
