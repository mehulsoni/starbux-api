package com.starbux.exception;

import java.io.IOException;

public class ReportGenerationException extends RuntimeException {

	public ReportGenerationException(String message) {
		super(message);
	}

	public ReportGenerationException(String message, Throwable exception) {
		super(message, exception);
	}
}
