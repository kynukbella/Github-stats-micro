package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Breakdown {
    private String language;
    private String editor;
    private int suggestionsCount;
    private int acceptancesCount;
    private int linesSuggested;
    private int linesAccepted;
    private int activeUsers;
}
