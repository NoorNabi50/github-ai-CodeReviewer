package com.Aicodeinsight.github_ai_reviewer.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GithubWebHook {
    X_GITHUB_EVENT("X-GitHub-Event"),
    X_HUB_SIGNATURE("X-Hub-Signature-256"),
    GITHUB_REQUEST_PATH("/api/webhooks/**");

    private final String name;
}
