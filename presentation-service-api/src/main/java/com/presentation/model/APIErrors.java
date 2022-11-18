package com.presentation.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

/**
 * This class is curated for the errors and exceptions that will arise in the
 * runtime.
 * 
 * @author HariharanB
 *
 */
public class APIErrors {

	private LocalDateTime timeestamp;
	private String message;
	private HttpStatus status;
	private int statusCode;
	private String error;

	public APIErrors() {
		super();
	}

	/**
	 * @param timeestamp
	 * @param message
	 * @param status
	 * @param statusCode
	 * @param error
	 */
	public APIErrors(LocalDateTime timeestamp, String message, HttpStatus status, int statusCode, String error) {
		super();
		this.timeestamp = timeestamp;
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
		this.error = error;
	}

	/**
	 * @return the timeestamp
	 */
	public LocalDateTime getTimeestamp() {
		return timeestamp;
	}

	/**
	 * @param timeestamp the timeestamp to set
	 */
	public void setTimeestamp(LocalDateTime timeestamp) {
		this.timeestamp = timeestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "APIErrors [timeestamp=" + timeestamp + ", message=" + message + ", status=" + status + ", statusCode="
				+ statusCode + ", error=" + error + "]";
	}

}
