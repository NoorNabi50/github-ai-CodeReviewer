package com.Aicodeinsight.github_ai_reviewer.service.codeReview;

import com.Aicodeinsight.github_ai_reviewer.dto.GithubWebHookPayload;
import com.Aicodeinsight.github_ai_reviewer.dto.ModelConfiguration;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OllamaCodeLlamaService implements ICodeReview{

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Override
    public String reviewCode(ModelConfiguration modelConfiguration, GithubWebHookPayload payload) {
        return "";
    }

    @Override
    public String getCodeReviewPrompt(String codeDiff) throws IOException {
        return "";
    }

    @Override
    public String getModelName() {
        return "";
    }
}
