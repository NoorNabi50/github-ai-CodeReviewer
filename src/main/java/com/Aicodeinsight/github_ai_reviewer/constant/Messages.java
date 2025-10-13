package com.Aicodeinsight.github_ai_reviewer.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    INVALID_EVENT("Invalid event type"),
    INVALID_SIGNATURE("Invalid signature");

    private final String message;
}
