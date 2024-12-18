# GitHub Repository Statistics Analyzer

This Spring Boot application provides an API to fetch and analyze statistics for top repositories of a given GitHub organization.

The GitHub Repository Statistics Analyzer is designed to collect and process data from GitHub repositories, offering insights into repository activity, user engagement, and code changes over time. It leverages the GitHub API to fetch repository data and generates historical statistics for analysis.

Key features include:
- Fetching top repositories of a specified GitHub organization
- Generating daily statistics for repositories over a 7-day period
- Providing breakdowns of repository data by language and metrics
- Simulating historical data with a decay factor for realistic trend analysis

## Repository Structure

```
.
├── src
│   └── main
│       └── java
│           └── org
│               └── example
│                   ├── configuration
│                   │   └── AppConfig.java
│                   ├── controller
│                   │   └── GitHubStatsController.java
│                   ├── model
│                   │   ├── Breakdown.java
│                   │   ├── ContributorStats.java
│                   │   ├── GitHubRepoDTO.java
│                   │   └── GitHubStats.java
│                   ├── service
│                   │   ├── GitHubApiClient.java
│                   │   └── GitHubStatsService.java
│                   └── GitHubStatsApplication.java
└── pom.xml
```

### Key Files:
- `GitHubStatsApplication.java`: The main entry point for the Spring Boot application.
- `GitHubStatsController.java`: REST controller that exposes the API endpoint for fetching weekly statistics.
- `GitHubApiClient.java`: Service for interacting with the GitHub API.
- `GitHubStatsService.java`: Service for processing and generating GitHub statistics.
- `AppConfig.java`: Configuration class for defining beans used in the application.

## Usage Instructions

### Prerequisites
- Java 17 or later
- Maven 3.6 or later
- GitHub API access (no authentication required for public repositories)

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd github-stats-analyzer
   ```

3. Build the project:
   ```
   mvn clean install
   ```

### Running the Application

1. Start the application:
   ```
   java -jar target/github-stats-analyzer-1.0.0.jar
   ```

2. The application will start and be available at `http://localhost:8080`.

### API Usage

To fetch weekly statistics for a GitHub organization:

```
GET /api/v1/github-stats/weekly-stats?org={organization}&limit={limit}
```

- `org`: The GitHub organization name (required)
- `limit`: The maximum number of repositories to analyze (optional, default: 5)

Example request:
```
curl "http://localhost:8080/api/v1/github-stats/weekly-stats?org=microsoft&limit=3"
```

Example response:
```json
{
  "dateRange": {
    "start": "2023-06-01",
    "end": "2023-06-07"
  },
  "repositories": {
    "vscode": [
      {
        "2023-06-01": {
          "repositoryName": "vscode",
          "day": "2023-06-01",
          "totalSuggestionsCount": 145000,
          "totalAcceptancesCount": 25000,
          "totalLinesSuggested": 5000000,
          "totalLinesAccepted": 50000,
          "totalActiveUsers": 1450,
          "totalChatAcceptances": 2500,
          "totalChatTurns": 14500,
          "totalActiveChatUsers": 2500,
          "breakdown": [
            {
              "language": "TypeScript",
              "editor": "main",
              "suggestionsCount": 145000,
              "acceptancesCount": 25000,
              "linesSuggested": 5000000,
              "linesAccepted": 50000,
              "activeUsers": 1450
            }
          ]
        }
      },
      // ... more dates
    ],
    // ... more repositories
  },
  "organization": "microsoft"
}
```

### Troubleshooting

1. Issue: Application fails to start
   - Ensure Java 17 or later is installed and set as the default Java version.
   - Check if port 8080 is available. If not, you can change the port in `application.properties`:
     ```
     server.port=8081
     ```

2. Issue: Unable to fetch GitHub data
   - Verify your internet connection.
   - Check if GitHub API is accessible (https://api.github.com).
   - If you're hitting rate limits, consider implementing GitHub authentication.

3. Debugging
   - Enable debug logging by adding the following to `application.properties`:
     ```
     logging.level.org.example=DEBUG
     ```
   - Restart the application and check the console for detailed logs.

4. Performance Optimization
   - Monitor API response times and adjust the `limit` parameter if necessary.
   - Consider implementing caching for frequently accessed data.
   - Use profiling tools like VisualVM to identify performance bottlenecks.

## Data Flow

The GitHub Stats Analyzer processes data through the following flow:

1. User sends a request to `/api/v1/github-stats/weekly-stats` with organization and limit parameters.
2. `GitHubStatsController` receives the request and calls `GitHubStatsService`.
3. `GitHubStatsService` uses `GitHubApiClient` to fetch top repositories from GitHub API.
4. Raw repository data is processed and historical stats are generated.
5. Processed data is transformed into the required response format.
6. The formatted response is sent back to the user.

```
[User] -> [GitHubStatsController] -> [GitHubStatsService] -> [GitHubApiClient]
                                                                  |
                                                                  v
[User] <- [GitHubStatsController] <- [GitHubStatsService] <- [GitHub API]
```

Note: The application simulates historical data using a decay factor, as it doesn't fetch actual historical data from GitHub.