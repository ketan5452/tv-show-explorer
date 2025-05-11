import React from 'react';
import { Link } from 'react-router-dom';
import './ShowCard.css';

function ShowCard({ show }) {
    // Basic handling for missing name
    const displayName = show?.title || 'Unknown Show';

    return (
        <div className="show-card">
            <h3>
                <Link to={`/shows/${encodeURIComponent(displayName)}`}>{displayName}</Link>
            </h3>
            {show?.summary && <p>{show.summary.substring(0, Math.min(show.summary.length, 100))}...</p>}
            {show?.network && <p>Network: {show.network}</p>}
        </div>
    );
}

export default ShowCard;