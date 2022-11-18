package com.presentation.exception;

public class TemplateNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TemplateNotFoundException() {
		super();
	}

	public TemplateNotFoundException(String message) {
		super(message);
	}

}
