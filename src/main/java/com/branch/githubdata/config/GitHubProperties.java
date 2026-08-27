package com.branch.githubdata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.api")
public record GitHubProperties(
		String baseUrl
) {
}
