package com.Aicodeinsight.github_ai_reviewer.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class ModelConfiguration {
    private String modelName;
    private double temperature;
}
