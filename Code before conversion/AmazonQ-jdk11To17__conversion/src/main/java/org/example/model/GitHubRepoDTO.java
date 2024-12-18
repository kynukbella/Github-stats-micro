package org.example.model;
import lombok.Data;

@Data
public class GitHubRepoDTO {
    private Long id;
    private String name;
    private String full_name;
    private String description;
    private int stargazers_count;
    private String language;
    private int forks_count;
    // Add other fields as needed
}