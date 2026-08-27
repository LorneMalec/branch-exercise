package com.branch.githubdata.service;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.branch.githubdata.service.GitHubCacheService;
import com.branch.githubdata.client.GitHubClient;
import com.branch.githubdata.client.model.GitHubUserResponse;
import com.branch.githubdata.client.model.GitHubRepositoryResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.*;

@SpringBootTest
class GitHubDataServiceTest {

	@Autowired
	private GitHubCacheService gitHubCacheService;

	@MockitoBean
	private GitHubClient gitHubClient;

	@Test
	void contextLoads() {
		assertNotNull(gitHubCacheService);
	}

	@Test
	void shouldCacheUser() {
		GitHubUserResponse user = getGitHubUserResponse();

		when(gitHubClient.getGitHubUser("octocat"))
				.thenReturn(user);

		gitHubCacheService.getUser("octocat");
		gitHubCacheService.getUser("octocat");

		verify(gitHubClient, times(1))
			.getGitHubUser("octocat");
	}

	@Test
	void shouldCacheRepositories() {
		List<GitHubRepositoryResponse> repositories = getGitHubRepositoryResponseList();

		when(gitHubClient.getGitHubRepositories("octocat"))
				.thenReturn(repositories);

		gitHubCacheService.getRepositories("octocat");
		gitHubCacheService.getRepositories("octocat");

		verify(gitHubClient, times(1))
			.getGitHubRepositories("octocat");
	}

	public GitHubUserResponse getGitHubUserResponse() {
		return new GitHubUserResponse(
				"octocat",
				"The Octocat",
				"https://avatars.githubusercontent.com/u/583231?v=4",
				"San Francisco",
				null,
				"https://api.github.com/users/octocat",
				Instant.parse("2011-01-25T18:44:36Z")
		);
	}

	public List<GitHubRepositoryResponse> getGitHubRepositoryResponseList() {
		return List.of(
				new GitHubRepositoryResponse(
					"boysenberry-repo-1",
					"https://api.github.com/repos/octocat/boysenberry-repo-1"
				)
		);
	}

}

