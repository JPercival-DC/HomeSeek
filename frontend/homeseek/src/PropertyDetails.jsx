import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./styles/PropertyDetails.css";
// Fix this path based on where your PropertyDetails.jsx is located
// Try one of these:
import defaultRoomPic from "./Assets/default_room_pic.jpg";  // If in subfolder
// OR
// import defaultRoomPic from "./Assets/default_room_pic.jpg";  // If in src folder

function PropertyDetails() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [property, setProperty] = useState(null);
    const [loading, setLoading] = useState(true);
    const [showBookingModal, setShowBookingModal] = useState(false);
    const [bookingMessage, setBookingMessage] = useState("");
    const [reviews, setReviews] = useState([]);
    const [newReview, setNewReview] = useState({ rating: 5, comment: "" });
    const [isFavorite, setIsFavorite] = useState(false);
    const [user, setUser] = useState(null);
    const [rating, setRating] = useState({ average: 0, count: 0 });
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        const loggedInUser = JSON.parse(localStorage.getItem("user"));
        setUser(loggedInUser);
    }, []);

    useEffect(() => {
        if (id) {
            fetchPropertyDetails();
            fetchReviews();
            fetchPropertyRating();
        }
    }, [id]);

    useEffect(() => {
        if (user && id) {
            checkIfFavorite();
        }
    }, [user, id]);

    const fetchPropertyDetails = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/properties/${id}`);
            if (response.ok) {
                const data = await response.json();
                setProperty(data);
            } else {
                alert("Property not found");
                navigate("/listings");
            }
        } catch (error) {
            console.error("Error fetching property:", error);
        } finally {
            setLoading(false);
        }
    };

    const fetchPropertyRating = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/reviews/property/${id}/rating`);
            if (response.ok) {
                const data = await response.json();
                setRating({
                    average: data.average || 0,
                    count: data.count || 0
                });
            }
        } catch (error) {
            console.error("Error fetching rating:", error);
        }
    };

    const fetchReviews = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/reviews/property/${id}`);
            if (response.ok) {
                const data = await response.json();
                console.log("Reviews fetched:", data);
                setReviews(data);
            }
        } catch (error) {
            console.error("Error fetching reviews:", error);
        }
    };

    const checkIfFavorite = async () => {
        if (!user) return;
        try {
            const response = await fetch(`http://localhost:8080/api/favorites/check?userId=${user.userId}&propertyId=${id}`);
            if (response.ok) {
                const data = await response.json();
                setIsFavorite(data);
            }
        } catch (error) {
            console.error("Error checking favorite:", error);
        }
    };

    const handleAddToFavorites = async () => {
        if (!user) {
            alert("Please login to add favorites");
            navigate("/login");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/favorites?userId=${user.userId}&propertyId=${id}`, {
                method: "POST"
            });

            if (response.ok) {
                setIsFavorite(true);
                alert("Added to favorites!");
            } else {
                alert("Failed to add to favorites");
            }
        } catch (error) {
            console.error("Error adding favorite:", error);
            alert("Error adding to favorites");
        }
    };

    const handleRemoveFromFavorites = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/favorites/remove?userId=${user.userId}&propertyId=${id}`, {
                method: "DELETE"
            });

            if (response.ok) {
                setIsFavorite(false);
                alert("Removed from favorites");
            } else {
                alert("Failed to remove from favorites");
            }
        } catch (error) {
            console.error("Error removing favorite:", error);
        }
    };

    const handleBookingRequest = async (e) => {
        e.preventDefault();
        if (!user) {
            alert("Please login to book this property");
            navigate("/login");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/bookings", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    tenantId: user.userId,
                    propertyId: parseInt(id),
                    message: bookingMessage || null
                })
            });

            const data = await response.json();
            console.log("Booking response:", data);

            if (response.ok && data.success) {
                alert("Booking request sent successfully!");
                setShowBookingModal(false);
                setBookingMessage("");
            } else {
                alert(data.error || "Failed to send booking request");
            }
        } catch (error) {
            console.error("Error booking:", error);
            alert("Error sending booking request");
        }
    };

    const handleSubmitReview = async (e) => {
        e.preventDefault();
        if (!user) {
            alert("Please login to leave a review");
            navigate("/login");
            return;
        }

        if (!newReview.comment.trim()) {
            alert("Please write a review comment");
            return;
        }

        setSubmitting(true);

        try {
            const response = await fetch("http://localhost:8080/api/reviews", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    userId: user.userId,
                    propertyId: parseInt(id),
                    rating: newReview.rating,
                    comment: newReview.comment
                })
            });

            const data = await response.json();
            console.log("Review response:", data);

            if (response.ok && data.success) {
                alert("Review submitted successfully!");
                setNewReview({ rating: 5, comment: "" });
                
                // Force refresh with a slight delay to ensure database commit
                setTimeout(() => {
                    fetchReviews();
                    fetchPropertyRating();
                }, 500);
            } else {
                alert(data.error || "Failed to submit review");
            }
        } catch (error) {
            console.error("Error submitting review:", error);
            alert("Error submitting review");
        } finally {
            setSubmitting(false);
        }
    };

    const renderStars = (ratingValue) => {
        const fullStars = Math.floor(ratingValue);
        const hasHalfStar = ratingValue % 1 >= 0.5;
        const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
        
        return (
            <>
                <span style={{ color: '#f59e0b', fontSize: '1.2rem' }}>{'★'.repeat(fullStars)}</span>
                {hasHalfStar && <span style={{ color: '#f59e0b', fontSize: '1.2rem' }}>½</span>}
                <span style={{ color: '#d1d5db', fontSize: '1.2rem' }}>{'☆'.repeat(emptyStars)}</span>
            </>
        );
    };

    if (loading) {
        return <div className="loading">Loading property details...</div>;
    }

    if (!property) {
        return <div className="loading">Property not found</div>;
    }

    return (
        <div className="property-details-page">
            <div className="details-container">
                {/* Property Image */}
                <div className="property-image-container">
                    <img 
                        src={defaultRoomPic} 
                        alt={property.propertyName} 
                        className="property-main-image"
                        onError={(e) => {
                            e.target.src = "https://via.placeholder.com/800x500?text=Property+Image";
                        }}
                    />
                </div>

                {/* Property Info */}
                <div className="property-info-section">
                    <div className="property-header">
                        <h1>{property.propertyName}</h1>
                        <div className="property-actions">
                            {isFavorite ? (
                                <button className="btn-favorite active" onClick={handleRemoveFromFavorites}>
                                    ❤️ Saved
                                </button>
                            ) : (
                                <button className="btn-favorite" onClick={handleAddToFavorites}>
                                    🤍 Save to Favorites
                                </button>
                            )}
                        </div>
                    </div>

                    <div className="property-location">
                        📍 {property.address}
                    </div>

                    <div className="property-price">
                        ₱{property.price.toLocaleString()}/month
                    </div>

                    {/* Rating Display */}
                    <div className="property-rating-section">
                        <div className="rating-stars">
                            {renderStars(rating.average)}
                            <span className="rating-text">
                                {rating.average > 0 ? rating.average.toFixed(1) : "No ratings"} 
                                ({rating.count} {rating.count === 1 ? "review" : "reviews"})
                            </span>
                        </div>
                    </div>

                    <div className="property-meta">
                        <span className="meta-item">🏠 Type: {property.type || "N/A"}</span>
                        <span className="meta-item">🛏️ Rooms: {property.rooms || "N/A"}</span>
                        <span className={`meta-item status ${property.availabilityStatus === "Available" ? "available" : "unavailable"}`}>
                            {property.availabilityStatus === "Available" ? "✅ Available" : "❌ Not Available"}
                        </span>
                    </div>

                    <div className="property-description">
                        <h3>Description</h3>
                        <p>{property.description || "No description provided."}</p>
                    </div>

                    {/* Booking Button */}
                    {property.availabilityStatus === "Available" && (
                        <button className="btn-book" onClick={() => setShowBookingModal(true)}>
                            Request Booking
                        </button>
                    )}
                </div>

                {/* Reviews Section */}
                <div className="reviews-section">
                    <h2>Reviews ({reviews.length})</h2>
                    
                    {/* Add Review Form */}
                    {user && (
                        <form className="review-form" onSubmit={handleSubmitReview}>
                            <h3>Write a Review</h3>
                            <div className="rating-input">
                                <label>Rating:</label>
                                <select 
                                    value={newReview.rating} 
                                    onChange={(e) => setNewReview({...newReview, rating: parseInt(e.target.value)})}
                                    disabled={submitting}
                                >
                                    <option value={5}>★★★★★ (5)</option>
                                    <option value={4}>★★★★☆ (4)</option>
                                    <option value={3}>★★★☆☆ (3)</option>
                                    <option value={2}>★★☆☆☆ (2)</option>
                                    <option value={1}>★☆☆☆☆ (1)</option>
                                </select>
                            </div>
                            <textarea
                                placeholder="Write your review here..."
                                value={newReview.comment}
                                onChange={(e) => setNewReview({...newReview, comment: e.target.value})}
                                required
                                rows="4"
                                disabled={submitting}
                            />
                            <button type="submit" className="btn-submit-review" disabled={submitting}>
                                {submitting ? "Submitting..." : "Submit Review"}
                            </button>
                        </form>
                    )}

                    {/* Reviews List */}
                    <div className="reviews-list">
                        {reviews.length === 0 ? (
                            <p className="no-reviews">No reviews yet. Be the first to review!</p>
                        ) : (
                            reviews.map((review) => (
                                <div key={review.reviewID} className="review-card">
                                    <div className="review-header">
                                        <strong>{review.user?.name || "Anonymous"}</strong>
                                        <span className="review-rating">
                                            {"★".repeat(review.rating)}{"☆".repeat(5 - review.rating)}
                                        </span>
                                    </div>
                                    <p className="review-date">{review.reviewDate}</p>
                                    <p className="review-comment">{review.comment}</p>
                                </div>
                            ))
                        )}
                    </div>
                </div>
            </div>

            {/* Booking Modal */}
            {showBookingModal && (
                <div className="modal-overlay" onClick={() => setShowBookingModal(false)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h2>Request Booking</h2>
                        <form onSubmit={handleBookingRequest}>
                            <p><strong>Property:</strong> {property.propertyName}</p>
                            <p><strong>Price:</strong> ₱{property.price.toLocaleString()}/month</p>
                            <textarea
                                placeholder="Additional message (optional)"
                                value={bookingMessage}
                                onChange={(e) => setBookingMessage(e.target.value)}
                                rows="4"
                            />
                            <div className="modal-buttons">
                                <button type="submit" className="btn-submit">Send Request</button>
                                <button type="button" className="btn-cancel" onClick={() => setShowBookingModal(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default PropertyDetails;