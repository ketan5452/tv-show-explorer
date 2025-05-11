import React from 'react';
import useFetch from '../hooks/useFetch';
import ShowCard from './ShowCard';
import './ShowList.css';

function ShowList() {
    const { data: shows, loading, error } = useFetch('http://localhost:8080/api/shows');

    if (loading) {
        return <div>Loading shows...</div>;
    }

    if (error) {
        return <div>Error loading shows.</div>;
    }

    return (
        <div className="show-list">
            {shows && shows.map(show => (
                <ShowCard key={show.title} show={show} />
            ))}
        </div>
    );
}

export default ShowList;