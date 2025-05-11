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
