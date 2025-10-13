package com.Aicodeinsight.github_ai_reviewer.service.gitService;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class GithubService implements IGitService {

    @Override
    public String fetchCodeDiff(String repoOwner, String repoName, String commitSha) {
        return null;
    }
}
