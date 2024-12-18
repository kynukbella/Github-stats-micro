package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Breakdown;
import org.example.model.ContributorStats;
import org.example.model.GitHubStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GitHubApiClient {
    private static final String GITHUB_API_URL = "https://api.github.com/orgs";

    private final RestTemplate restTemplate;

    @Autowired
    public GitHubApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Change return type to List<Map<String, Object>>
    public List<Map<String, Object>> fetchTopOrgRepos(String org, int limit) {
        String url = String.format("%s/%s/repos?sort=stars&direction=desc&per_page=%d", GITHUB_API_URL, org, limit);

        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.error("Error fetching repos: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    private GitHubStats convertToGitHubStats(Map<String, Object> repo) {
        GitHubStats stats = new GitHubStats();

        // Extract data from GitHub API response
        stats.setRepositoryName(getStringValue(repo, "name"));
        stats.setDay(LocalDate.now().toString());
        stats.setTotalSuggestionsCount(getIntValue(repo, "stargazers_count"));
        stats.setTotalAcceptancesCount(getIntValue(repo, "forks_count"));
        stats.setTotalLinesSuggested(getIntValue(repo, "size")); // Repository size as lines
        stats.setTotalLinesAccepted(getIntValue(repo, "open_issues_count"));
        stats.setTotalActiveUsers(getIntValue(repo, "watchers_count"));
        stats.setTotalChatAcceptances(getIntValue(repo, "network_count"));
        stats.setTotalChatTurns(getIntValue(repo, "subscribers_count"));
        stats.setTotalActiveChatUsers(getIntValue(repo, "watchers"));

        // Create breakdown with actual repository data
        Breakdown breakdown = new Breakdown();
        breakdown.setLanguage(getStringValue(repo, "language"));
        breakdown.setEditor(getStringValue(repo, "default_branch")); // Use default branch as editor
        breakdown.setSuggestionsCount(getIntValue(repo, "stargazers_count"));
        breakdown.setAcceptancesCount(getIntValue(repo, "forks_count"));
        breakdown.setLinesSuggested(getIntValue(repo, "size"));
        breakdown.setLinesAccepted(getIntValue(repo, "open_issues_count"));
        breakdown.setActiveUsers(getIntValue(repo, "watchers_count"));

        stats.setBreakdown(Collections.singletonList(breakdown));
        return stats;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .map(Object::toString)
                .orElse("");
    }

    private int getIntValue(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .map(value -> {
                    if (value instanceof Integer) {
                        return (Integer) value;
                    }
                    try {
                        return Integer.parseInt(value.toString());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .orElse(0);
    }
}