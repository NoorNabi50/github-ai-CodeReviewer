package com.Aicodeinsight.github_ai_reviewer.config;

import com.Aicodeinsight.github_ai_reviewer.dto.ModelConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;

@Service
public class ModelConfigLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ModelConfiguration getSelectedModel() {
        String model = "mistral";
        double temperature = 0.2;

        try {
            File file = new ClassPathResource("config/modelConfig.json").getFile();
            JsonNode rootNode = objectMapper.readTree(file);

            model = rootNode.path("selectedModel").asText();
            JsonNode modelNode = rootNode.path("models").path(model);

            if (!modelNode.isMissingNode() && modelNode.path("enabled").asBoolean(false)) {
                temperature = modelNode.path("temperature").asDouble(temperature);
            }

        } catch (Exception ex) {
            System.out.println("Error reading modelConfig.json: " + ex.getMessage());
        }

        return new ModelConfiguration(model, temperature);
    }

}
