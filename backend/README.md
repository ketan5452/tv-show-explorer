## Backend Application Setup & Run Instructions

### Prerequisites

- Java 17+
- Maven 3.8+
- Internet connection (for fetching data from TVMaze)
- Git (optional)

### Download and Extract the source code zip file

```bash
cd tv-show-explorer/backend
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

## API Endpoints

Method	Endpoint	Description
GET	/api/shows	Returns a list of all fetched TV shows.
GET	/api/shows/{title}	Returns details of a specific TV show by title (case-insensitive).

Example:

```bash
curl http://localhost:8080/api/shows
curl http://localhost:8080/api/shows/friends
```

## Project Structure
```bash

src/
 └── main/
     ├── java/
     │    └── com.example.tvshowapp/
     │         ├── controller/        # REST controllers
     │         ├── service/           # Business logic
     │         ├── integration/       # TVMaze client
     │         └── model/             # Show DTOs
     └── resources/
          └── tvtitles.txt           # Input file for titles
```

## Assumptions & Design Decisions
The application uses the TVMaze public API without authentication.

Title normalization is performed before querying the API (e.g., removing special characters, redundant spaces).

The tvtitles.txt file is assumed to be available at runtime in the classpath.

Asynchronous loading of show data is triggered during application startup.

A simple in-memory cache (ConcurrentHashMap) is used to store show data.

There is a 500ms delay between API calls to prevent hitting TVMaze rate limits.

### Future Improvements:

Include (TDD) Tests (currently excluded & skipped)

Implement pagination for large data sets.

Add persistent storage using a database.

Implement retries and error handling for failed API requests.

Introduce caching (e.g., Redis) for performance in production.

## Author
Ketan Deshmukh

2025-05-11
