package com.Aicodeinsight.github_ai_reviewer.service.codeReview;

import com.Aicodeinsight.github_ai_reviewer.config.ModelConfigLoader;
import com.Aicodeinsight.github_ai_reviewer.dto.GithubWebHookPayload;
import com.Aicodeinsight.github_ai_reviewer.dto.ModelConfiguration;
import org.springframework.ai.model.ChatModelDescription;

import java.io.IOException;

public interface ICodeReview {

    String reviewCode(ModelConfiguration modelConfiguration, GithubWebHookPayload payload);
    String getCodeReviewPrompt(String codeDiff) throws IOException;
    String getModelName();
}
