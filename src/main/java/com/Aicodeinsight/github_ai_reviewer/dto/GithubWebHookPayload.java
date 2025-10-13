package com.Aicodeinsight.github_ai_reviewer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubWebHookPayload {
    private String after; // latest commit SHA
}
