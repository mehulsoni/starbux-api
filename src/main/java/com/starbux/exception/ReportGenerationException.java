package com.starbux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportGenerationException extends RuntimeException {

	public ReportGenerationException(String message) {
		super(message);
	}

	public ReportGenerationException(String message, Throwable exception) {
		super(message, exception);
	}
}
