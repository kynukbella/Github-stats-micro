package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.model.ContributorStats;
import org.example.model.GitHubStats;
import org.example.model.Breakdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GitHubStatsService {
    private final GitHubApiClient githubApiClient;
    private final Random random;
    private static final double DAILY_DECAY_RATE = 0.02;

    @Autowired
    public GitHubStatsService(GitHubApiClient githubApiClient, Random random) {
        this.githubApiClient = githubApiClient;
        this.random = random;
    }

    public List<GitHubStats> getTopOrgRepos(String org, int limit) {
        validateInput(org, limit);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        try {
            List<Map<String, Object>> repos = githubApiClient.fetchTopOrgRepos("microsoft",5);
            return generateHistoricalStats(repos, startDate, endDate);
        } catch (Exception e) {
            log.error("Failed to fetch repos for org {}: {}", org, e.getMessage());
            throw new RuntimeException("Failed to fetch repository data", e);
        }
    }

    private List<GitHubStats> generateHistoricalStats(List<Map<String, Object>> repos, LocalDate startDate, LocalDate endDate) {
        List<GitHubStats> allStats = new ArrayList<>();

        for (Map<String, Object> repo : repos) {
            RepoMetrics baseMetrics = extractBaseMetrics(repo);

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                double decayFactor = calculateDecayFactor(date, endDate);
                GitHubStats dailyStats = createDailyStats(repo, date, baseMetrics, decayFactor);
                allStats.add(dailyStats);
            }
        }

        return allStats;
    }

    private GitHubStats createDailyStats(Map<String, Object> repo, LocalDate date, RepoMetrics metrics, double decayFactor) {
        int dailyStars = (int)(metrics.getStars() * decayFactor);
        int dailyForks = (int)(metrics.getForks() * decayFactor);

        GitHubStats stats = new GitHubStats();
        stats.setRepositoryName(extractStringValue(repo, "name"));
        stats.setDay(date.toString());
        stats.setTotalSuggestionsCount(dailyStars);
        stats.setTotalAcceptancesCount(dailyForks);
        stats.setTotalLinesSuggested(metrics.getSize());
        stats.setTotalLinesAccepted(generateRandomMetric(metrics.getSize(), 0.01));
        stats.setTotalActiveUsers(calculateActiveUsers(dailyStars));
        stats.setTotalChatAcceptances(generateRandomMetric(dailyForks, 0.1));
        stats.setTotalChatTurns(generateRandomMetric(dailyStars, 0.1));
        stats.setTotalActiveChatUsers(calculateActiveChatUsers(dailyForks));

        stats.setBreakdown(Collections.singletonList(createBreakdown(repo, metrics, dailyStars, dailyForks)));

        return stats;
    }

    private Breakdown createBreakdown(Map<String, Object> repo, RepoMetrics metrics, int dailyStars, int dailyForks) {
        return new Breakdown(
                extractStringValue(repo, "language"),
                extractStringValue(repo, "default_branch"),
                dailyStars,
                dailyForks,
                metrics.getSize(),
                generateRandomMetric(metrics.getSize(), 0.01),
                calculateActiveUsers(dailyStars)
        );
    }

    @Data
    @AllArgsConstructor
    private static class RepoMetrics {
        private final int stars;
        private final int forks;
        private final int size;
    }

    private RepoMetrics extractBaseMetrics(Map<String, Object> repo) {
        return new RepoMetrics(
                extractIntValue(repo, "stargazers_count"),
                extractIntValue(repo, "forks_count"),
                extractIntValue(repo, "size")
        );
    }

    private double calculateDecayFactor(LocalDate currentDate, LocalDate endDate) {
        long daysFromEnd = ChronoUnit.DAYS.between(currentDate, endDate);
        return Math.max(0.7, 1.0 - (daysFromEnd * DAILY_DECAY_RATE));
    }

    private int calculateActiveUsers(int baseMetric) {
        return Math.max(1, baseMetric / 100);
    }

    private int calculateActiveChatUsers(int baseMetric) {
        return Math.max(1, baseMetric / 10);
    }

    private int generateRandomMetric(int baseMetric, double factor) {
        return random.nextInt((int)(baseMetric * factor) + 1);
    }

    private String extractStringValue(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .map(Object::toString)
                .orElse("");
    }

    private int extractIntValue(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .map(value -> {
                    if (value instanceof Integer) {
                        return (Integer) value;
                    }
                    try {
                        return Integer.parseInt(value.toString());
                    } catch (NumberFormatException e) {
                        log.warn("Failed to parse integer for key {}: {}", key, value);
                        return 0;
                    }
                })
                .orElse(0);
    }

    private void validateInput(String org, int limit) {
        if (StringUtils.isEmpty(org)) {
            throw new IllegalArgumentException("Organization name is required");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }
    }
}