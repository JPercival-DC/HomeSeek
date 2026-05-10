import { useState, useEffect } from "react";
import PropertyCard from "./Components/PropertyCard";
import ListingNav from "./navbar/listing-nav";

function Listings() {
    const [properties, setProperties] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchProperties();
    }, []);
    
    const fetchProperties = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/properties");
            if (response.ok) {
                const data = await response.json();
                console.log("Fetched properties in Listings:", data); // Debug
                setProperties(data);
            }
        } catch (error) {
            console.error("Error fetching properties:", error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="featured-v2"><p>Loading properties...</p></div>;
    }

    return (
        <div>
            <section className="featured-v2">
                <div>
                    <ListingNav />
                </div>

                <div className="listing-properties">
                    {properties.length === 0 ? (
                        <p>No properties found.</p>
                    ) : (
                        properties.map((property) => (
                            <PropertyCard
                                key={property.propertyId}
                                property={property}
                                large={true}
                            />
                        ))
                    )}
                </div>
            </section>
        </div>
    );
}

export default Listings;