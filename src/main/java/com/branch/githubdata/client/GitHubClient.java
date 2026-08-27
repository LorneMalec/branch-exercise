package com.branch.githubdata.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;

import com.branch.githubdata.client.model.GitHubUserResponse;
import com.branch.githubdata.client.model.GitHubRepositoryResponse;
import com.branch.githubdata.exception.GitHubUserNotFoundException;

@Component
public class GitHubClient {

	private static final Logger LOG = LoggerFactory.getLogger(GitHubClient.class);

	private final RestClient restClient;

	public GitHubClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public GitHubUserResponse getGitHubUser(String username) {

		GitHubUserResponse response = null;

		LOG.debug("GitHub request: GET /users/{}", username);
		try {

			response = restClient.get()
				.uri("/users/{username}", username)
				.retrieve()
				.body(GitHubUserResponse.class);
		}
		catch (HttpClientErrorException.NotFound ex) {
			LOG.warn("GitHub user not found: {}", username);
			throw new GitHubUserNotFoundException(username);
		}
		return response;
	}

	public List<GitHubRepositoryResponse> getGitHubRepositories(String username) {

		List<GitHubRepositoryResponse> response = null;

		LOG.debug("GitHub request: GET /users/{}/repos", username);

		try {
			response = restClient.get()
				.uri("/users/{username}/repos", username)
				.retrieve()
				.body(new ParameterizedTypeReference<List<GitHubRepositoryResponse>>() {});
		}
		catch (HttpClientErrorException.NotFound ex) {
			LOG.warn("GitHub user not found: {}", username);
			throw new GitHubUserNotFoundException(username);
		}
		return response != null ? response : List.of();
	}

}

