package com.branch.githubdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.branch.githubdata.dto.RepositorySummaryDto;

public record UserRepositoriesDto(

		@JsonProperty("user_name")
		String userName,

		@JsonProperty("display_name")
		String displayName,

		String avatar,

		@JsonProperty("geo_location")
		String geoLocation,

		String email,

		String url,

		@JsonProperty("created_at")
		String createdAt,

		List<RepositorySummaryDto> repos
	) {
}
