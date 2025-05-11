import React from 'react';
import { useParams, Link } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './ShowDetails.css';

function ShowDetails() {
    const { title } = useParams();
    const { data: show, loading, error } = useFetch(`http://localhost:8080/api/shows/${encodeURIComponent(title)}`);

    if (loading) {
        return <div>Loading show details...</div>;
    }

    if (error) {
        return <div>Error loading show details.</div>;
    }

    if (!show) {
        return <div>Show not found.</div>;
    }

    return (
        <div className="show-details">
            <h2>{show.title}</h2>
            {show.summary && <div className="summary" dangerouslySetInnerHTML={{ __html: show.summary }} />}
            <div className="show-info">
                {show.network && <p><strong>Network:</strong> {show.network}</p>}
                {show.schedule && <p><strong>Schedule:</strong> {show.schedule}</p>}
                {show.status && <p><strong>Status:</strong> {show.status}</p>}
            </div>
            <Link to="/">Back to List</Link>
        </div>
    );
}

export default ShowDetails;