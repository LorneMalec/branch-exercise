package com.branch.githubdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GitHubDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubDataApplication.class, args);
	}

}
