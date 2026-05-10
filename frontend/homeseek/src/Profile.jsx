import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./styles/Profile.css";
import defaultRoomPic from "./Assets/default_room_pic.jpg";

function Profile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [userProperties, setUserProperties] = useState([]);
    const [userBookings, setUserBookings] = useState([]);
    const [userFavorites, setUserFavorites] = useState([]);
    const [userReviews, setUserReviews] = useState([]);
    const [activeTab, setActiveTab] = useState("profile");
    const [loading, setLoading] = useState(true);
    const [editMode, setEditMode] = useState(false);
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        phone: "",
        currentPassword: "",
        newPassword: "",
        confirmPassword: ""
    });

    useEffect(() => {
        const loggedInUser = JSON.parse(localStorage.getItem("user"));
        if (!loggedInUser) {
            navigate("/login");
            return;
        }
        setUser(loggedInUser);
        setFormData({
            name: loggedInUser.name,
            email: loggedInUser.email,
            phone: loggedInUser.phone || "",
            currentPassword: "",
            newPassword: "",
            confirmPassword: ""
        });
        fetchUserData(loggedInUser.userId);
    }, [navigate]);

    const fetchUserData = async (userId) => {
        setLoading(true);
        try {
            // Fetch user's properties
            const propertiesRes = await fetch(`http://localhost:8080/api/properties/owner/${userId}`);
            if (propertiesRes.ok) {
                setUserProperties(await propertiesRes.json());
            }

            // Fetch user's bookings
            const bookingsRes = await fetch(`http://localhost:8080/api/bookings/tenant/${userId}`);
            if (bookingsRes.ok) {
                setUserBookings(await bookingsRes.json());
            }

            // Fetch user's favorites
            const favoritesRes = await fetch(`http://localhost:8080/api/favorites/user/${userId}`);
            if (favoritesRes.ok) {
                setUserFavorites(await favoritesRes.json());
            }

            // Fetch user's reviews
            const reviewsRes = await fetch(`http://localhost:8080/api/reviews/user/${userId}`);
            if (reviewsRes.ok) {
                setUserReviews(await reviewsRes.json());
            }
        } catch (error) {
            console.error("Error fetching user data:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleUpdateProfile = async (e) => {
        e.preventDefault();
        
        if (formData.newPassword && formData.newPassword !== formData.confirmPassword) {
            alert("New passwords do not match!");
            return;
        }

        try {
            const updateData = {
                name: formData.name,
                email: formData.email,
                phone: formData.phone,
                role: user.role
            };

            if (formData.newPassword) {
                updateData.password = formData.newPassword;
            }

            const response = await fetch(`http://localhost:8080/api/users/update/${user.userId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(updateData)
            });

            if (response.ok) {
                const updatedUser = await response.json();
                localStorage.setItem("user", JSON.stringify(updatedUser));
                setUser(updatedUser);
                setEditMode(false);
                alert("Profile updated successfully!");
                window.location.reload();
            } else {
                alert("Failed to update profile");
            }
        } catch (error) {
            console.error("Error updating profile:", error);
            alert("Error updating profile");
        }
    };

    const handleCancelBooking = async (bookingId) => {
        if (window.confirm("Are you sure you want to cancel this booking?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}/cancel`, {
                    method: "PUT"
                });
                if (response.ok) {
                    alert("Booking cancelled!");
                    fetchUserData(user.userId);
                } else {
                    alert("Failed to cancel booking");
                }
            } catch (error) {
                console.error("Error cancelling booking:", error);
            }
        }
    };

    const handleRemoveFavorite = async (propertyId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/favorites/remove?userId=${user.userId}&propertyId=${propertyId}`, {
                method: "DELETE"
            });
            if (response.ok) {
                alert("Removed from favorites");
                fetchUserData(user.userId);
            }
        } catch (error) {
            console.error("Error removing favorite:", error);
        }
    };

    const handleDeleteReview = async (reviewId) => {
        if (window.confirm("Are you sure you want to delete this review?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
                    method: "DELETE"
                });
                
                const data = await response.json();
                console.log("Delete response:", data);
                
                if (response.ok) {
                    alert("Review deleted successfully!");
                    // Refresh the reviews list
                    fetchUserData(user.userId);
                } else {
                    alert(data.error || "Failed to delete review");
                }
            } catch (error) {
                console.error("Error deleting review:", error);
                alert("Error deleting review");
            }
        }
    };

    if (loading) {
        return <div className="loading">Loading profile...</div>;
    }

    return (
        <div className="profile-page">
            <div className="profile-container">
                {/* Profile Header */}
                <div className="profile-header">
                    <div className="profile-avatar">
                        <div className="avatar-circle">
                            {user?.name?.charAt(0)?.toUpperCase()}
                        </div>
                    </div>
                    <div className="profile-info">
                        <h1>{user?.name}</h1>
                        <p className="profile-email">{user?.email}</p>
                        <p className="profile-role">
                            Role: <span className={`role-badge role-${user?.role}`}>{user?.role}</span>
                        </p>
                        {user?.phone && <p className="profile-phone">📞 {user.phone}</p>}
                    </div>
                    {!editMode && (
                        <button className="btn-edit-profile" onClick={() => setEditMode(true)}>
                            ✏️ Edit Profile
                        </button>
                    )}
                </div>

                {/* Edit Profile Form */}
                {editMode && (
                    <div className="edit-profile-form">
                        <h3>Edit Profile</h3>
                        <form onSubmit={handleUpdateProfile}>
                            <div className="form-group">
                                <label>Full Name</label>
                                <input
                                    type="text"
                                    value={formData.name}
                                    onChange={(e) => setFormData({...formData, name: e.target.value})}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Email</label>
                                <input
                                    type="email"
                                    value={formData.email}
                                    onChange={(e) => setFormData({...formData, email: e.target.value})}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Phone Number</label>
                                <input
                                    type="tel"
                                    value={formData.phone}
                                    onChange={(e) => setFormData({...formData, phone: e.target.value})}
                                />
                            </div>
                            <div className="form-group">
                                <label>New Password (optional)</label>
                                <input
                                    type="password"
                                    placeholder="Enter new password"
                                    value={formData.newPassword}
                                    onChange={(e) => setFormData({...formData, newPassword: e.target.value})}
                                />
                            </div>
                            <div className="form-group">
                                <label>Confirm New Password</label>
                                <input
                                    type="password"
                                    placeholder="Confirm new password"
                                    value={formData.confirmPassword}
                                    onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                                />
                            </div>
                            <div className="form-buttons">
                                <button type="submit" className="btn-save">Save Changes</button>
                                <button type="button" className="btn-cancel" onClick={() => setEditMode(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                )}

                {/* Tabs */}
                <div className="profile-tabs">
                    <button className={`tab ${activeTab === "profile" ? "active" : ""}`} onClick={() => setActiveTab("profile")}>
                        My Properties
                    </button>
                    <button className={`tab ${activeTab === "bookings" ? "active" : ""}`} onClick={() => setActiveTab("bookings")}>
                        My Bookings
                    </button>
                    <button className={`tab ${activeTab === "favorites" ? "active" : ""}`} onClick={() => setActiveTab("favorites")}>
                        Favorites
                    </button>
                    <button className={`tab ${activeTab === "reviews" ? "active" : ""}`} onClick={() => setActiveTab("reviews")}>
                        My Reviews
                    </button>
                </div>

                {/* Tab Content */}
                <div className="tab-content">
                    {/* My Properties Tab */}
                    {activeTab === "profile" && (
                        <div className="properties-tab">
                            <div className="tab-header">
                                <h2>My Properties ({userProperties.length})</h2>
                                {(user?.role === "owner" || user?.role === "admin") && (
                                    <Link to="/ListProperty" className="btn-add-property">
                                        + Add New Property
                                    </Link>
                                )}
                            </div>
                            {userProperties.length === 0 ? (
                                <p className="empty-state">You haven't listed any properties yet.</p>
                            ) : (
                                <div className="properties-grid">
                                    {userProperties.map((property) => (
                                        <div key={property.propertyId} className="property-card">
                                            <img 
                                                src={defaultRoomPic} 
                                                alt={property.propertyName}
                                                onError={(e) => {
                                                    e.target.src = "https://via.placeholder.com/300x200?text=Property";
                                                }}
                                            />
                                            <div className="property-card-info">
                                                <h3>{property.propertyName}</h3>
                                                <p className="location">📍 {property.address}</p>
                                                <p className="price">₱{property.price.toLocaleString()}/mo</p>
                                                <p className={`status ${property.availabilityStatus === "Available" ? "available" : "unavailable"}`}>
                                                    {property.availabilityStatus}
                                                </p>
                                                <Link to={`/PropertyDetails/${property.propertyId}`} className="btn-view">
                                                    View Details
                                                </Link>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    )}

                    {/* My Bookings Tab */}
                    {activeTab === "bookings" && (
                        <div className="bookings-tab">
                            <h2>My Bookings ({userBookings.length})</h2>
                            {userBookings.length === 0 ? (
                                <p className="empty-state">You haven't made any bookings yet.</p>
                            ) : (
                                <div className="bookings-list">
                                    {userBookings.map((booking) => (
                                        <div key={booking.bookingID} className="booking-card">
                                            <div className="booking-info">
                                                <h3>{booking.property?.propertyName || "Property"}</h3>
                                                <p>Request Date: {new Date(booking.requestDate).toLocaleDateString()}</p>
                                                <p className={`booking-status status-${booking.status?.toLowerCase()}`}>
                                                    Status: {booking.status}
                                                </p>
                                                {booking.message && <p>Message: {booking.message}</p>}
                                            </div>
                                            {booking.status === "PENDING" && (
                                                <button className="btn-cancel-booking" onClick={() => handleCancelBooking(booking.bookingID)}>
                                                    Cancel Request
                                                </button>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    )}

                    {/* Favorites Tab */}
                    {activeTab === "favorites" && (
                        <div className="favorites-tab">
                            <h2>My Favorites ({userFavorites.length})</h2>
                            {userFavorites.length === 0 ? (
                                <p className="empty-state">You haven't added any favorites yet.</p>
                            ) : (
                                <div className="favorites-grid">
                                    {userFavorites.map((favorite) => (
                                        <div key={favorite.favoriteID} className="favorite-card">
                                            <img 
                                                src={defaultRoomPic} 
                                                alt={favorite.property?.propertyName || "Property"}
                                                onError={(e) => {
                                                    e.target.src = "https://via.placeholder.com/300x200?text=Property";
                                                }}
                                            />
                                            <div className="favorite-info">
                                                <h3>{favorite.property?.propertyName || "Property"}</h3>
                                                <p className="location">📍 {favorite.property?.address || "Address not specified"}</p>
                                                <p className="price">₱{favorite.property?.price?.toLocaleString() || "N/A"}/mo</p>
                                                <p className="saved-date">⭐ Saved on: {new Date(favorite.dateSaved).toLocaleDateString()}</p>
                                                <div className="favorite-actions">
                                                    <Link to={`/PropertyDetails/${favorite.property?.propertyId}`} className="btn-view">
                                                        View Details
                                                    </Link>
                                                    <button className="btn-remove" onClick={() => handleRemoveFavorite(favorite.property?.propertyId)}>
                                                        Remove
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    )}

                    {/* My Reviews Tab */}
                    {activeTab === "reviews" && (
                        <div className="reviews-tab">
                            <h2>My Reviews ({userReviews.length})</h2>
                            {userReviews.length === 0 ? (
                                <p className="empty-state">You haven't written any reviews yet.</p>
                            ) : (
                                <div className="reviews-list">
                                    {userReviews.map((review) => (
                                        <div key={review.reviewID} className="review-card">
                                            <div className="review-header">
                                                <h3>{review.property?.propertyName || "Property"}</h3>
                                                <span className="review-rating">
                                                    {"★".repeat(review.rating)}{"☆".repeat(5 - review.rating)}
                                                </span>
                                            </div>
                                            <p className="review-date">📅 {review.reviewDate}</p>
                                            <p className="review-comment">💬 "{review.comment}"</p>
                                            <div className="review-actions">
                                                <Link to={`/PropertyDetails/${review.property?.propertyId}`} className="btn-view-property">
                                                    View Property
                                                </Link>
                                                <button 
                                                    className="btn-delete-review" 
                                                    onClick={() => handleDeleteReview(review.reviewID)}
                                                >
                                                    🗑️ Delete Review
                                                </button>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Profile;