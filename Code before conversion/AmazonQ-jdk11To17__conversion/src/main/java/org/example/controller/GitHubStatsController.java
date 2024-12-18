package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.model.GitHubStats;
import org.example.service.GitHubStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/github-stats")
@Slf4j
public class GitHubStatsController {
    private final GitHubStatsService githubStatsService;

    @Autowired
    public GitHubStatsController(GitHubStatsService githubStatsService) {
        this.githubStatsService = githubStatsService;
    }

    @GetMapping("/weekly-stats")
    public ResponseEntity<Map<String, Object>> getWeeklyStats(
            @RequestParam String org,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            log.info("Fetching weekly stats for organization: {}", org);

            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(6);

            List<GitHubStats> weeklyStats = githubStatsService.getTopOrgRepos(org, limit);

            // Transform data structure
            Map<String, List<Map<String, GitHubStats>>> transformedStats = weeklyStats.stream()
                    .collect(Collectors.groupingBy(
                            GitHubStats::getRepositoryName,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    list -> list.stream()
                                            .map(stat -> {
                                                // Create date-keyed object
                                                Map<String, GitHubStats> dateObj = new HashMap<>();
                                                dateObj.put(stat.getDay(), stat);
                                                return dateObj;
                                            })
                                            .collect(Collectors.toList())
                            )
                    ));

            Map<String, Object> response = new HashMap<>();
            response.put("dateRange", Map.of(
                    "start", startDate.toString(),
                    "end", endDate.toString()
            ));
            response.put("repositories", transformedStats);
            response.put("organization", org);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error fetching weekly stats: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to fetch weekly statistics"));
        }
    }
}