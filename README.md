# TV Show App

A Spring Boot-based backend application that fetches and stores TV show details using the [TVMaze API](https://www.tvmaze.com/api). This application reads a list of TV show titles from a file and asynchronously fetches their data from the TVMaze service. It also exposes REST endpoints to retrieve show information.

---

## Follow below steps to run frontend and backend application:

### Prerequisites

1. Docker 26+

## Steps to run app with docker

1. Download the source code zip file
2. Extract the content of zip file
3. Go to tv-show-explorer directory
4. Run following command to run app

	`docker compose up --build`

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

## Frontend Application Setup & Run Instructions

### Prerequisites

- Node.js 18+
- npm (Node Package Manager)
- Internet connection (to download dependencies)
- Git (optional)
- Download and Extract the source code zip file
```bash
cd tv-show-explorer/frontend
```

### Install Dependencies

Using npm:

```
npm install
```

### Run the Application in Development Mode
```
npm start
```

This will start the React development server, and the application should be accessible at http://localhost:3000 in your browser.

### Build the Application for Production
```
npm run build
```

This command will create an optimized production build of the application in the build directory. This build is what is served by the Nginx server in the Docker container.

## Frontend Project Structure
```
frontend/
 ├── public/              # Static assets
 ├── src/
 │   ├── App.js           # Main application component
 │   ├── components/      # Reusable UI components
 │   │   ├── ShowCard.js
 │   │   ├── ShowList.js
 │   │   ├── ShowDetails.js
 │   │   ├── ShowCard.css
 │   │   ├── ShowList.css
 │   │   └── ShowDetails.css
 │   ├── hooks/           # Custom React hooks
 │   │   └── useFetch.js
 │   ├── index.js         # Entry point of the application
 │   └── App.css          # Global styles
 └── package.json         # Project dependencies and scripts
```
## Assumptions & Design Decisions

The frontend makes API calls to the backend server running on http://localhost:8080. This is configured within the useFetch hook.

Basic error handling and loading states are implemented in the frontend components.

The UI provides a simple listing of TV shows with a brief summary on the card and a detailed view upon clicking a show.

Styling is done using basic CSS modules (imported .css files).

## Future Improvements:

Implement more sophisticated UI elements and styling (e.g., using a UI library).

Add search and filtering functionality to the TV show list.

Improve error handling and user feedback.

Implement client-side routing for a smoother user experience.

Consider adding unit and integration tests for frontend components.

## Author
Ketan Deshmukh

2025-05-11