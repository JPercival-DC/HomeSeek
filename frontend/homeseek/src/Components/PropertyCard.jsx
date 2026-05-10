import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import "./PropertyCard.css";
import defaultRoomPic from "../Assets/default_room_pic.jpg";

function PropertyCard({ property, large }) {
    const [rating, setRating] = useState({ average: 0, count: 0 });
    const [loadingRating, setLoadingRating] = useState(true);
    
    // Use the local image
    const propertyImage = defaultRoomPic;
    
    // Fetch real ratings from backend
    useEffect(() => {
        if (property.propertyId) {
            fetch(`http://localhost:8080/api/reviews/property/${property.propertyId}/rating`)
                .then(res => res.json())
                .then(data => {
                    console.log(`Rating for ${property.propertyName}:`, data);
                    setRating({
                        average: data.average || 0,
                        count: data.count || 0
                    });
                    setLoadingRating(false);
                })
                .catch(err => {
                    console.error("Rating fetch error:", err);
                    setLoadingRating(false);
                });
        }
    }, [property.propertyId]);
    
    // Function to render stars
    const renderStars = (ratingValue) => {
        const fullStars = Math.floor(ratingValue);
        const hasHalfStar = ratingValue % 1 >= 0.5;
        const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
        
        return (
            <>
                <span style={{ color: '#f59e0b' }}>{'★'.repeat(fullStars)}</span>
                {hasHalfStar && <span style={{ color: '#f59e0b' }}>½</span>}
                <span style={{ color: '#d1d5db' }}>{'☆'.repeat(emptyStars)}</span>
            </>
        );
    };
    
    return (
        <div className={`property-card ${large ? "property-card-large" : ""}`}>
            <img 
                src={propertyImage} 
                alt={property.propertyName || "Property"} 
                onError={(e) => {
                    e.target.src = "https://via.placeholder.com/300x200?text=No+Image";
                }}
            />
            <div className="property-info">
                <div>
                    <h3>{property.propertyName || "Property Name"}</h3>
                    
                    <div className="price-loc">
                        <p className="location">📍 {property.address || "Address not specified"}</p>
                        <p className="price">₱{(property.price || 0).toLocaleString()}/month</p>
                    </div>

                    {/* Real Rating Stars */}
                    <div className="property-rating">
                        <div className="stars">
                            {!loadingRating ? (
                                <>
                                    {renderStars(rating.average)}
                                </>
                            ) : (
                                <span style={{ color: '#d1d5db' }}>☆☆☆☆☆</span>
                            )}
                        </div>
                        <span className="rating-value">
                            {!loadingRating ? (
                                <>
                                    {rating.average > 0 ? rating.average : "No"} 
                                    ({rating.count} {rating.count === 1 ? "review" : "reviews"})
                                </>
                            ) : (
                                "Loading..."
                            )}
                        </span>
                    </div>

                    {/* Type and Rooms */}
                    <div className="property-type">
                        <span className="type-badge">
                            🏠 {property.type || "Not specified"}
                        </span>
                        <span className="rooms-badge">
                            🛏️ {property.rooms || "?"} {property.rooms === 1 ? "room" : "rooms"}
                        </span>
                    </div>

                    {/* Show description only for large cards (listings page) */}
                    {large && property.description && (
                        <div className="extra-info">
                            <p className="description">{property.description}</p>
                        </div>
                    )}
                </div>

                <div className="link-button">
                    <Link className="detail-link" to={`/PropertyDetails/${property.propertyId}`}>
                        View Details →
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default PropertyCard;