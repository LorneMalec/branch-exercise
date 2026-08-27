package com.branch.githubdata.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import com.branch.githubdata.client.GitHubClient;
import com.branch.githubdata.client.model.GitHubUserResponse;
import com.branch.githubdata.client.model.GitHubRepositoryResponse;

@Service
public class GitHubCacheService {

	private static final Logger LOG = LoggerFactory.getLogger(GitHubCacheService.class);

	private final GitHubClient gitHubClient;

	public GitHubCacheService(GitHubClient gitHubClient) {
		this.gitHubClient = gitHubClient;
	}

	@Cacheable(
		cacheNames = "github-users",
		key = "#username",
		sync = true
	)
	public GitHubUserResponse getUser(String username) {
		LOG.debug("Cache MISS: github-users username={}", username);
		return gitHubClient.getGitHubUser(username);
	}

	@Cacheable(
		cacheNames = "github-repositories",
		key = "#username",
		sync = true
	)
	public List<GitHubRepositoryResponse> getRepositories(String username) {
		LOG.debug("Cache MISS: github-repositories username={}", username);
		return gitHubClient.getGitHubRepositories(username);
	}

}


