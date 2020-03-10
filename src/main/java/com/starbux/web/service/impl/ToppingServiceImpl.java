package com.starbux.web.service.impl;

import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.entity.Topping;
import com.starbux.exception.NotFoundException;
import com.starbux.web.mapper.IToppingMapper;
import com.starbux.web.repository.IToppingRepository;
import com.starbux.web.service.IToppingService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToppingServiceImpl implements IToppingService<ToppingReqDto, ToppingResDto> {

	@Autowired
	private IToppingRepository toppingRepository;

	@Autowired
	private IToppingMapper toppingMapper;

	@Override
	public Optional<ToppingResDto> create(ToppingReqDto toppingReqDto) {
		Topping topping = transformToEntity(toppingReqDto);
		Topping savedTopping = toppingRepository.save(topping);
		return Optional.ofNullable(transformToResponse(savedTopping));
	}

	@Override
	public Optional<ToppingResDto> update(ToppingReqDto toppingReqDto) {
		Topping topping = transformToEntity(toppingReqDto);
		Topping updatedTopping = toppingRepository.save(topping);
		return Optional.ofNullable(transformToResponse(updatedTopping));
	}

	@Override
	public Optional<ToppingResDto> get(Long id) {
		Optional<Topping> topping = toppingRepository.findById(id);
		if (topping.isPresent()) {
			return Optional.of(transformToResponse(topping.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<ToppingResDto>> getAll() {
		try {
			List<Topping> toppings = toppingRepository.findAll();
			if (CollectionUtils.isNotEmpty(toppings)) {
				return Optional.of(transformToResponse(toppings));
			}
			return Optional.empty();
		} catch (Exception ex) {
			throw new NotFoundException("Not Found");
		}
	}

	@Override
	public Optional<ToppingResDto> enable(Long id) {
		return toggle(id, true);
	}

	private Optional<ToppingResDto> toggle(Long id, boolean flag) {
		Optional<Topping> toppingOptional = toppingRepository.findById(id);
		if (toppingOptional.isPresent()) {
			Topping topping = toppingOptional.get();
			topping.setEnabled(flag);
			topping = toppingRepository.save(topping);
			return Optional.ofNullable(transformToResponse(topping));
		}
		return Optional.empty();
	}

	@Override
	public Optional<ToppingResDto> disable(Long id) {
		return toggle(id, false);
	}

	private ToppingResDto transformToResponse(Topping topping) {
		return toppingMapper.entityToResponseDto(topping);
	}

	private Topping transformToEntity(ToppingReqDto toppingReq) {
		return toppingMapper.requestDtoToEntity(toppingReq);
	}

	private List<ToppingResDto> transformToResponse(List<Topping> toppings) {
		return toppingMapper.entitiesToResponseDtoList(toppings);
	}

}
