package com.Aicodeinsight.github_ai_reviewer.config;

import com.Aicodeinsight.github_ai_reviewer.constant.Messages;
import com.Aicodeinsight.github_ai_reviewer.constant.ValidWebHookEvents;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.stream.Collectors;
import static com.Aicodeinsight.github_ai_reviewer.constant.GithubWebHook.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${github.webhook.secret.key}")
    private String gitHubWebHookSecretKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(GITHUB_REQUEST_PATH.getName())
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(GITHUB_REQUEST_PATH.getName()).permitAll()
                        .anyRequest().authenticated()
                ) .addFilterBefore(requestAuhenticationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public Filter requestAuhenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


                // Skip authentication if isTestMode=true
                String isTestMode = request.getParameter("isTestMode");
                if ("true".equalsIgnoreCase(isTestMode)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
                String eventType = cachedRequest.getHeader(X_GITHUB_EVENT.getName());
                ValidWebHookEvents event = ValidWebHookEvents.valueOf(eventType.toUpperCase());

                if(!ValidWebHookEvents.getAllValidEvents().contains(event)){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(Messages.INVALID_EVENT.getMessage());
                    return;
                }

                String signature = cachedRequest.getHeader(X_HUB_SIGNATURE.getName());
                String payload = cachedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

                if(!isValidSignature(payload,signature)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(Messages.INVALID_SIGNATURE.getMessage());
                    return;
                }

               filterChain.doFilter(cachedRequest, response);
            }
        };
    }

    private boolean isValidSignature(String payload, String signature){
        boolean isValid = false;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKeySpec = new SecretKeySpec(gitHubWebHookSecretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));

            String computedSignature = "sha256="+bytesToHex(hash);

            isValid =  MessageDigest.isEqual(computedSignature.getBytes(), signature.getBytes());
        } catch (Exception ex) {
            System.out.println("Error while validating signature: " + ex.getMessage()); // TODO: Add Sl4j logger here
        }

        return isValid;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}