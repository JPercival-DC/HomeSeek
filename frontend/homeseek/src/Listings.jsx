import { useState, useEffect } from "react";
import PropertyCard from "./Components/PropertyCard";
import ListingNav from "./navbar/listing-nav";

function Listings() {
    const [properties, setProperties] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/properties")
            .then(res => res.json())
            .then(data => setProperties(data))
            .catch(err => console.log("Error fetching properties:", err));
    }, []);

    return (
        <div>

            <section className="featured-v2">

                <div>
                    <ListingNav />
                </div>

                <div className="listing-properties">
                    {properties.map((property) => (
                        <PropertyCard
                            key={property.propertyId}
                            property={property}
                            large
                        />
                    ))}
                </div>
            </section>
        </div>
    );
}
export default Listings;