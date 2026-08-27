package com.branch.githubdata.client.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubUserResponse(

		String login,

		String name,

		@JsonProperty("avatar_url")
		String avatarUrl,

		String location,

		String email,

		String url,

		@JsonProperty("created_at")
		Instant createdAt

) {
}

