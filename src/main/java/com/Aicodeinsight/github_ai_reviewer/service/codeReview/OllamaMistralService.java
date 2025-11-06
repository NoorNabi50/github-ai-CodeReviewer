package com.Aicodeinsight.github_ai_reviewer.service.codeReview;

import com.Aicodeinsight.github_ai_reviewer.constant.ModelNames;
import com.Aicodeinsight.github_ai_reviewer.dto.GithubWebHookPayload;
import com.Aicodeinsight.github_ai_reviewer.dto.ModelConfiguration;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service("ollama-mistral")
public class OllamaMistralService implements ICodeReview {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Override
    public String reviewCode(ModelConfiguration modelConfiguration, GithubWebHookPayload payload) {
        String responseText = "";
        try {
            String codeDiff = """
                    def greet(name) print("Hi " + name)
                    names = "Noor","Anna","Lukas"
                    for n in names print(greet(n))
                    greetings=[greet(n)for n in names]
                    print "done"
                    """;

            Prompt prompt = new Prompt(getCodeReviewPrompt(codeDiff), OllamaOptions.builder()
                    .model(modelConfiguration.getModelName())
                    .temperature(modelConfiguration.getTemperature())
                    .build());

            ChatResponse response = ollamaChatModel.call(prompt);
            responseText = response.getResult().getOutput().getText();
            System.out.println("AI Code Review Response: " + responseText);
        } catch (Exception ex) {
            throw new RuntimeException("Error during code review: " + ex.getMessage());
        }
        return responseText;
    }

    @Override
    public String getCodeReviewPrompt(String codeDiff) throws IOException {
        File file = new ClassPathResource("templates/codeReviewPrompt").getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    @Override
    public String getModelName() {
        return ModelNames.MISTRAL.getModelName();
    }
}