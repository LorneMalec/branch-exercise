package com.branch.githubdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.branch.githubdata.config.GitHubProperties;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

	@Bean
	public RestClient getHubRestClient(
			RestClient.Builder builder,
			GitHubProperties properties) {
		return builder
			.baseUrl(properties.baseUrl())
			.defaultHeader(
					"Accept",
					"application/vnd.github+json")
			.build();
	}

}

