package com.Aicodeinsight.github_ai_reviewer.service.gitService;

public interface IGitService {

    public String fetchCodeDiff(String repoOwner, String repoName, String commitSha);
}
