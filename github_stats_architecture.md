# GitHub Stats Application Architecture

## High-Level Architecture Diagram

```
┌───────────────┐     ┌──────────────────┐     ┌─────────────────┐     ┌──────────────┐
│    Client     │────▶│GitHubController  │────▶│GitHubService    │────▶│GitHubAPI     │
│  (HTTP/REST)  │     │(REST Endpoints)  │     │(Business Logic) │     │(External API)│
└───────────────┘     └──────────────────┘     └─────────────────┘     └──────────────┘
```

## Low-Level Component Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    GitHubStatsApplication                       │
├─────────────────┬────────────────────────┬────────────────────┬┤
│   Controller    │       Service          │     API Client     ││
│                 │                        │                     ││
│ - GET /weekly   │ - getTopOrgRepos      │ - fetchTopOrgRepos ││
│   -stats        │ - generateHistorical   │ - convertToGitHub  ││
│                 │   Stats                │   Stats            ││
└─────────────────┴────────────────────────┴────────────────────┴┘
```

## Service Logic Flow

1. **Entry Point**: GitHubStatsController
   - Handles HTTP requests for GitHub statistics
   - Endpoint: `/api/v1/github-stats/weekly-stats`
   - Parameters: organization name, limit, start date, end date

2. **Business Logic**: GitHubStatsService
   - Validates input parameters
   - Fetches top repositories for an organization
   - Generates historical statistics with decay factors
   - Creates daily stats and breakdowns
   - Handles metrics calculations:
     * Active users
     * Chat users
     * Stars and forks
     * Repository metrics

3. **API Integration**: GitHubApiClient
   - Communicates with GitHub API
   - Fetches repository data
   - Converts API responses to domain objects

## Key Components

1. **Models**:
   - GitHubStats: Core data model
   - Breakdown: Statistics breakdown
   - ContributorStats: Contributor information
   - GitHubRepoDTO: Data transfer object

2. **Configuration**:
   - AppConfig: Spring configuration
   - RestTemplate configuration
   - Random number generator for metrics

## Technical Stack

- Java 11
- Spring Boot 2.7.0
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Service Logic Details

The service layer implements the following key functionalities:

1. **Repository Data Retrieval**
   - Fetches top repositories based on stars
   - Limits results to specified count
   - Handles API pagination

2. **Historical Data Generation**
   - Creates historical statistics for date range
   - Applies decay factors for temporal analysis
   - Generates synthetic metrics for analysis

3. **Metrics Processing**
   - Calculates active users
   - Processes chat statistics
   - Tracks stars and forks
   - Generates random variations for metrics

4. **Data Validation**
   - Input parameter validation
   - Organization name verification
   - Limit bounds checking
   - Date range validation

The application uses a combination of real GitHub API data and generated metrics to provide comprehensive repository statistics and analysis.