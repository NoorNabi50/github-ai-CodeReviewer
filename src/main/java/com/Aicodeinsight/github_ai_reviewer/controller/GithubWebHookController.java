package com.Aicodeinsight.github_ai_reviewer.controller;

import com.Aicodeinsight.github_ai_reviewer.dto.GithubWebHookPayload;
import com.Aicodeinsight.github_ai_reviewer.service.codeReview.CodeReviewServiceFactory;
import com.Aicodeinsight.github_ai_reviewer.service.codeReview.ICodeReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks")
public class GithubWebHookController {

    @Autowired
    private CodeReviewServiceFactory codeReviewServiceFactory;

    @PostMapping("/github")
    public String handleGitHubWebhook(@RequestBody(required = false) GithubWebHookPayload payload) {
         return codeReviewServiceFactory.executeCodeReview(payload);
    }
}
