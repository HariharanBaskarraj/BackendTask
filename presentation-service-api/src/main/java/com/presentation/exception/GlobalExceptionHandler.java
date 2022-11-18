package com.presentation.exception;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.presentation.model.APIErrors;

/**
 * This class is curated for adding headers to the exceptions that will arise during the execution of the program
 * 
 * @author HariharanB
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		String error = "Request Method Not Supported";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, status, status.value(), error);
		headers.add("info", message);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		String error = "Media type is Not Supported";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, status, status.value(), error);
		headers.add("info", message);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		String error = "Missing path variable";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, status, status.value(), error);
		headers.add("info", message);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		String error = "Missing request parameter";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, status, status.value(), error);
		headers.add("info", message);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		String error = "Type mismatch";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, status, status.value(), error);
		headers.add("info", message);
		return ResponseEntity.status(status).headers(headers).body(errors);
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(FieldNotFoundException.class)
	public ResponseEntity<Object> handleFieldNotFound(FieldNotFoundException ex) {
		String message = ex.getMessage();
		String error = "Field Not Found Exception";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, HttpStatus.NOT_ACCEPTABLE,
				HttpStatus.NOT_ACCEPTABLE.value(), error);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(errors);
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TemplateNotFoundException.class)
	public ResponseEntity<Object> handleTemplateNotFound(TemplateNotFoundException ex) {
		String message = ex.getMessage();
		String error = "Template Not Found Exception";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), error);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(errors);
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<Object> handleFileNotFound(FileNotFoundException ex) {
		String message = ex.getMessage();
		String error = "File Not Found Exception";
		APIErrors errors = new APIErrors(LocalDateTime.now(), message, HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), error);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", message);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(errors);
	}
}
