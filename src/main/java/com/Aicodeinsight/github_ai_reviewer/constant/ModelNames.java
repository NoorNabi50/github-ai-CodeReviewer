package com.Aicodeinsight.github_ai_reviewer.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ModelNames
{
    MISTRAL("mistral");
    private final String modelName;
}
