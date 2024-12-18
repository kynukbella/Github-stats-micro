package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubStats {
    private String repositoryName;
    private String day;
    private int totalSuggestionsCount;
    private int totalAcceptancesCount;
    private int totalLinesSuggested;
    private int totalLinesAccepted;
    private int totalActiveUsers;
    private int totalChatAcceptances;
    private int totalChatTurns;
    private int totalActiveChatUsers;
    private List<Breakdown> breakdown;
}
