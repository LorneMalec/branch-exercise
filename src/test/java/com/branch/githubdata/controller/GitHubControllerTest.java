package com.branch.githubdata.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.branch.githubdata.service.GitHubDataService;
import com.branch.githubdata.dto.UserRepositoriesDto;
import com.branch.githubdata.dto.RepositorySummaryDto;
import com.branch.githubdata.exception.GitHubUserNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.*;

@WebMvcTest(GitHubDataController.class)
class GitHubDataControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private GitHubDataService gitHubDataService;

	@Test
	void shouldReturnValidGitHubUser() throws Exception {
		UserRepositoriesDto dto = getUserRepositoriesDto();

		when(gitHubDataService.getUserRepositories("octocat"))
				.thenReturn(dto);

		mockMvc.perform(get("/api/v1/githubdata/users/octocat"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.user_name").value("octocat"))
			.andExpect(jsonPath("$.display_name").value("The Octocat"))
			.andExpect(jsonPath("$.avatar").value(
				"https://avatars.githubusercontent.com/u/583231?v=4"))
			.andExpect(jsonPath("$.geo_location").value("San Francisco"))
			.andExpect(jsonPath("$.email").doesNotExist())
			.andExpect(jsonPath("$.url").value(
				"https://api.github.com/users/octocat"))
			.andExpect(jsonPath("$.created_at").value(
				"Tue, 25 Jan 2011 18:44:36 GMT"))
			.andExpect(jsonPath("$.repos").isArray())
			.andExpect(jsonPath("$.repos.length()").value(1))
			.andExpect(jsonPath("$.repos[0].name").value(
				"boysenberry-repo-1"))
			.andExpect(jsonPath("$.repos[0].uri").value(
				"https://api.github.com/repos/octocat/boysenberry-repo-1"));
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"octocat!test",
		"octocat_test",
		"octocat.test",
		"-octocat",
		"octocat-",
		"octo--cat"
	})
	void shouldRejectInvalidGitHubUsername(String username) throws Exception {
		mockMvc.perform(get(
				"/api/v1/githubdata/users/{username}",
				username))
			.andExpect(status().isBadRequest());
	}

	@Test
	void shouldReturnNotFoundForUnknownGitHubUser() throws Exception {
		when(gitHubDataService.getUserRepositories("unknownuser"))
			.thenThrow(new GitHubUserNotFoundException("unknownuser"));

		mockMvc.perform(get(
				"/api/v1/githubdata/users/{username}",
				"unknownuser"))
			.andExpect(status().isNotFound());
	}

	public UserRepositoriesDto getUserRepositoriesDto() {
		return new UserRepositoriesDto(
				"octocat",
				"The Octocat",
				"https://avatars.githubusercontent.com/u/583231?v=4",
				"San Francisco",
				null,
				"https://api.github.com/users/octocat",
				"Tue, 25 Jan 2011 18:44:36 GMT",
				List.of(
					new RepositorySummaryDto(
						"boysenberry-repo-1",
						"https://api.github.com/repos/octocat/boysenberry-repo-1"
					)
				)
		);
	}

}
