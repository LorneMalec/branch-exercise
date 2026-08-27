package com.branch.githubdata.controller;

import jakarta.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;

import com.branch.githubdata.service.GitHubDataService;
import com.branch.githubdata.dto.UserRepositoriesDto;

@RestController
@RequestMapping("/api/v1/githubdata")
@Validated
public class GitHubDataController {

	private static final Logger LOG = LoggerFactory.getLogger(GitHubDataController.class);

	private final GitHubDataService gitHubDataService;

	public GitHubDataController(GitHubDataService gitHubDataService) {
		this.gitHubDataService = gitHubDataService;
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<UserRepositoriesDto> getUserRepositories(
			@PathVariable
			@Pattern(
            	regexp = "^(?!.*--)[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,37}[a-zA-Z0-9])?$",
            	message = "Invalid GitHub username"
			)
			String username) {

		LOG.info("Retrieving the GitHub User Repositories for username={}", username);
		UserRepositoriesDto userRepositoriesDto = gitHubDataService.getUserRepositories(username);
		return ResponseEntity.ok(userRepositoriesDto);
	}

}
