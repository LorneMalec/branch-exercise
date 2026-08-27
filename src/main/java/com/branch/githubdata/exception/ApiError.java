package com.branch.githubdata.exception;

public record ApiError(
	String code,
	String message
	) {
}
