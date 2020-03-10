package com.starbux.dto.request;

import com.starbux.enums.Command;

public class ToppingOrderDto {

	private Long id;
	private Command command;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
