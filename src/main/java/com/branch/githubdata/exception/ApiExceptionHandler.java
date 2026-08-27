package com.branch.githubdata.exception;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.branch.githubdata.exception.ApiError;
import com.branch.githubdata.exception.GitHubUserNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(GitHubUserNotFoundException.class)
	public ResponseEntity<ApiError> handleGitHubUserNotFound(
			GitHubUserNotFoundException ex) {

		LOG.warn("GitHub User not found: {}", ex.getMessage());

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new ApiError(
				HttpStatus.NOT_FOUND.toString(),
				ex.getMessage()
			));
	}

	//@ExceptionHandler(ConstraintViolationException.class)
	@ExceptionHandler({
		HandlerMethodValidationException.class,
		ConstraintViolationException.class
	})
	public ResponseEntity<ApiError> handleConstraintViolation(
			ConstraintViolationException ex) {

		LOG.warn("Request validation failed: {}", ex.getMessage());

		return ResponseEntity
			.badRequest()
			.body(new ApiError(
				HttpStatus.BAD_REQUEST.toString(),
				"Validation failed"
			));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleUnexpectedException(Exception ex) {

		LOG.error("Unexpected or internal application error", ex);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiError(
				"INTERNAL_SERVER_ERROR",
				"Internal or unexpected error occured"
			));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleUnexpectedException(RuntimeException ex) {

		LOG.error("Unexpected or internal application error", ex);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiError(
				"INTERNAL_SERVER_ERROR",
				"Internal or unexpected error occured"
			));
	}

}
