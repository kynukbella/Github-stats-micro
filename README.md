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
│                   │   └── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   ├── controller
│                   │   └── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   ├── model
│                   │   ├── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   │   ├── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   │   ├── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   │   └── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   ├── service
│                   │   ├── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   │   └── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
│                   └── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
└── https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
```

### Key Files:
- `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`: The main entry point for the Spring Boot application.
- `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`: REST controller that exposes the API endpoint for fetching weekly statistics.
- `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`: Service for interacting with the GitHub API.
- `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`: Service for processing and generating GitHub statistics.
- `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`: Configuration class for defining beans used in the application.

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
   java -jar https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
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
   - Check if port 8080 is available. If not, you can change the port in `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`:
     ```
     https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
     ```

2. Issue: Unable to fetch GitHub data
   - Verify your internet connection.
   - Check if GitHub API is accessible (https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip).
   - If you're hitting rate limits, consider implementing GitHub authentication.

3. Debugging
   - Enable debug logging by adding the following to `https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip`:
     ```
     https://raw.githubusercontent.com/kynukbella/Github-stats-micro/main/Code before conversion/AmazonQ-jdk11To17__conversion/Github_stats_micro_1.7.zip
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