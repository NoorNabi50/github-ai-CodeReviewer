package com.Aicodeinsight.github_ai_reviewer.service.codeReview;

import com.Aicodeinsight.github_ai_reviewer.config.ModelConfigLoader;
import com.Aicodeinsight.github_ai_reviewer.dto.GithubWebHookPayload;
import com.Aicodeinsight.github_ai_reviewer.dto.ModelConfiguration;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CodeReviewServiceFactory {

    private final Map<String, ICodeReview> services;
    private final ModelConfigLoader configLoader;

    public CodeReviewServiceFactory(List<ICodeReview> serviceList, ModelConfigLoader configLoader) {
        this.configLoader = configLoader;
        this.services = serviceList.stream().collect(Collectors.toMap(ICodeReview::getModelName, service -> service));
    }

    public String executeCodeReview(GithubWebHookPayload payload) {
        ModelConfiguration selectedModelConfig = configLoader.getSelectedModel();
        ICodeReview service = getService(selectedModelConfig.getModelName());

        if (service == null) {
            throw new RuntimeException("No code review service found for the selected model.");
        }

        return service.reviewCode(selectedModelConfig, payload);
    }

    public ICodeReview getService(String modelName) {
        return services.get(modelName);
    }
}
