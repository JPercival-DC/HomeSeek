import { useState, useEffect } from "react";
import PropertyCard from "./Components/PropertyCard";
import { Link } from "react-router-dom";

function Home() {
    const [properties, setProperties] = useState([]);

    // fetch from database
    useEffect(() => {
        fetch("http://localhost:8080/api/properties")
            .then(res => res.json())
            .then(data => setProperties(data))
            .catch(err => console.log(err));
    }, []);

    // manual featured selection
    const featuredIds = [2, 3, 8];

    const featuredProperties = properties.filter(
        (p) => featuredIds.includes(p.propertyId)
    );
    return (
        <div>
            <section className="hero">
                <h1>Seek your Perfect Home</h1>
            </section>

            <section className="featured">

                <div className="featured-top">

                    <div className="featured-header">
                        <h2>Featured Listing</h2>
                        <p>Hand-picked apartments just for you</p>
                    </div>

                    <div className="featured-button">
                        <Link to="/listings" className="btn btn--dark-text">
                            View All Listings</Link>
                    </div>
                </div>

                <div className="featured-cards">
                    {featuredProperties.map((property) => (
                        <PropertyCard
                            key={property.propertyId}
                            property={property}
                        />
                    ))}
                </div>
            </section>

            <section className="sec-3">
                <h1>Ready to Find Your New Home?</h1>
                <p>Join the thousands of happy renters who found their perfect place with HomeSeek</p>
                <div className="browse-links">
                    <Link to="/listings" className="btn btn--dark-text">Browse Listings</Link>
                    <Link to="/ListProperty" className="btn btn--outline">List Your Property</Link>
                </div>
            </section>
        </div>
    );

}

export default Home;