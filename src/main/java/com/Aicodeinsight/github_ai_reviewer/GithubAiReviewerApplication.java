package com.Aicodeinsight.github_ai_reviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GithubAiReviewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubAiReviewerApplication.class, args);
	}

}
