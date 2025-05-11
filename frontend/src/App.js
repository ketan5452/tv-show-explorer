import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ShowList from './components/ShowList';
import ShowDetails from './components/ShowDetails';
import './App.css';

function App() {
    return (
        <Router>
            <div className="app">
                <header className="app-header">
                    <h1>TV Show Explorer</h1>
                </header>
                <main className="app-main">
                    <Routes>
                        <Route path="/" element={<ShowList />} />
                        <Route path="/shows/:title" element={<ShowDetails />} />
                    </Routes>
                </main>
                <footer className="app-footer">
                    <p>&copy; 2025 TV Show App</p>
                </footer>
            </div>
        </Router>
    );
}

export default App;