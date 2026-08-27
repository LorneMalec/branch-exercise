# GitHub User Repository Service

Spring Boot service that retrieves GitHub user information and repositories from the GitHub REST API.

## Requirements

- Java 26
- Internet access

## Run

```bash
./gradlew clean build
./gradlew bootRun
```

The service runs at `http://localhost:8080`.

## API

Retrieve a GitHub user and their repositories:

```bash
curl -i http://localhost:8080/api/v1/githubdata/users/octocat
```

Example response:

```json
{
    user_name: "octocat",
    display_name: "The Octocat",
    avatar: "https://avatars.githubusercontent.com/u/583231?v=4",
    geo_location: "San Francisco",
    email: null,
    url: "https://api.github.com/users/octocat",
    created_at: "Tue, 25 Jan 2011 18:44:36 GMT",
    repos: [{
        name: "boysenberry-repo-1",
        url: "https://api.github.com/repos/octocat/boysenberry-repo-1
        }
    ]
}
```

## Caching

GitHub user and repository responses are cached separately for 10 minutes using Caffeine.

Run the same request twice to verify caching:

```bash
curl http://localhost:8080/api/v1/githubdata/users/octocat
curl http://localhost:8080/api/v1/githubdata/users/octocat
```

The first request retrieves data from GitHub. The second uses the cached response.

## Tests

```bash
./gradlew test
```

Test results are written to:

```text
build/reports/tests/test/index.html
```

## Health Check

```bash
curl http://localhost:8080/actuator/health
```

Expected response:

```json
{"status":"UP"}
```
