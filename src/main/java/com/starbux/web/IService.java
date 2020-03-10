package com.starbux.web;

import java.util.List;

public interface IService<T, U> {
	U create(T t);
	
	U update(T t);

	U get(Long id);

	List<U> getAll();

	U enable(Long id);

	U disable(Long id);
}
