package com.Aicodeinsight.github_ai_reviewer.constant;

import java.util.EnumSet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ValidWebHookEvents {
    PUSH("push");

    public final String event;
    public static EnumSet<ValidWebHookEvents> getAllValidEvents() {
        return EnumSet.allOf(ValidWebHookEvents.class);
    }
}
