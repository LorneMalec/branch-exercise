package com.branch.githubdata.service;

import java.util.List;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.branch.githubdata.service.GitHubCacheService;
import com.branch.githubdata.client.model.GitHubUserResponse;
import com.branch.githubdata.client.model.GitHubRepositoryResponse;
import com.branch.githubdata.dto.UserRepositoriesDto;
import com.branch.githubdata.dto.RepositorySummaryDto;

@Service
public class GitHubDataService {

	private static final Logger LOG = LoggerFactory.getLogger(GitHubDataService.class);

	private final GitHubCacheService gitHubCacheService;

	public GitHubDataService(GitHubCacheService gitHubCacheService) {
		this.gitHubCacheService = gitHubCacheService;
	}

	public UserRepositoriesDto getUserRepositories(String username) {

		LOG.info("Retrieving the GitHub User Repositories for username={}", username);

		GitHubUserResponse gitHubUser = gitHubCacheService.getUser(username);

		List<GitHubRepositoryResponse> gitHubRepositories = gitHubCacheService.getRepositories(username);

		List<RepositorySummaryDto> repos = gitHubRepositories.stream()
			.map(repo -> new RepositorySummaryDto(
				repo.name(),
				repo.url()
			))
			.toList();

		UserRepositoriesDto userRepositoriesDto = new UserRepositoriesDto(
				gitHubUser.login(),
				gitHubUser.name(),
				gitHubUser.avatarUrl(),
				gitHubUser.location(),
				gitHubUser.email(),
				gitHubUser.url(),
				DateTimeFormatter.RFC_1123_DATE_TIME.format(gitHubUser.createdAt().atZone(ZoneOffset.UTC)),
				repos);

		return userRepositoriesDto;
	}

}

