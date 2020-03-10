package com.starbux.web.service.impl;

import com.starbux.dto.request.ToppingReqDto;
import com.starbux.dto.response.ToppingResDto;
import com.starbux.entity.Topping;
import com.starbux.exception.NotFoundException;
import com.starbux.web.mapper.IToppingMapper;
import com.starbux.web.repository.IToppingRepository;
import com.starbux.web.service.IToppingService;

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
	public ToppingResDto create(ToppingReqDto toppingReqDto) {
		Topping topping = transformToEntity(toppingReqDto);
		Topping savedTopping = toppingRepository.save(topping);
		return transformToResponse(savedTopping);
	}

	@Override
	public ToppingResDto update(ToppingReqDto toppingReqDto) {
		Topping topping = transformToEntity(toppingReqDto);
		Topping updatedTopping = toppingRepository.save(topping);
		return transformToResponse(updatedTopping);
	}

	@Override
	public ToppingResDto get(Long id) {
		Optional<Topping> topping = toppingRepository.findById(id);
		if (topping.isPresent()) {
			return transformToResponse(topping.get());
		}
		throw new NotFoundException(
				String.format("Topping not found for given id {}", id));
	}

	@Override
	public List<ToppingResDto> getAll() {
		return transformToResponse(toppingRepository.findAll());
	}

	@Override
	public ToppingResDto enable(Long id) {
		return toggle(id, true);
	}

	private ToppingResDto toggle(Long id, boolean flag) {
		Optional<Topping> toppingOptional = toppingRepository.findById(id);
		if (toppingOptional.isPresent()) {
			Topping topping = toppingOptional.get();
			topping.setEnabled(flag);
			topping = toppingRepository.save(topping);
			return transformToResponse(topping);
		}
		throw new NotFoundException(
				String.format("Topping not found for given id {}", id));
	}

	@Override
	public ToppingResDto disable(Long id) {
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
