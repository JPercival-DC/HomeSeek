<<<<<<< HEAD
import { useState } from "react";
import PropertyCard from "./Components/PropertyCard";   
import ListingNav from "./navbar/listing-nav";
import properties from "./data/properties";

function Listings() {

    const [priceFilter, setPriceFilter] = useState("all");

    const filteredProperties = properties.filter((property) => {
        if (priceFilter === "low") return property.price < 3000;
        if (priceFilter === "mid") return property.price >= 3000 && property.price <= 5000;
        if (priceFilter === "high") return property.price > 4500;
=======
import { useState, useEffect } from "react";
import PropertyCard from "./Components/PropertyCard";
import ListingNav from "./navbar/listing-nav";
import defaultProperties from "./data/properties";

function Listings() {
    const [properties, setProperties] = useState([]);
    const [priceFilter, setPriceFilter] = useState("all");

    useEffect(() => {
        const stored = JSON.parse(localStorage.getItem("properties")) || [];

        const combined = [...defaultProperties, ...stored];

        setProperties(combined);
    }, []);

    const filteredProperties = properties.filter((property) => {
        if (priceFilter === "low") return property.price < 3000;
        if (priceFilter === "mid") return property.price >= 3000 && property.price <= 5000;
        if (priceFilter === "high") return property.price > 5000;
>>>>>>> 8930ca1b81e7fa098e07f23bd308a156a6d3ce7b
        return true;
    });

    return (
        <div>

            <section className="featured">

                <div className="filter-container">
<<<<<<< HEAD
                    <ListingNav/>
=======
                    <ListingNav />

>>>>>>> 8930ca1b81e7fa098e07f23bd308a156a6d3ce7b
                    <div>
                        <h4>Filter by Price</h4>

                        <label>
<<<<<<< HEAD
                            <input type="radio" name="price" value="all" checked={priceFilter === "all"}
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> All </label>

                        <label>
                            <input type="radio" name="price" value="low" onChange={(e) => setPriceFilter(e.target.value)}
                            />  below 3000 </label>

                        <label>
                            <input type="radio" name="price" value="mid"
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> 3000 - 5000 </label>

                        <label>
                            <input type="radio"  name="price" value="high"
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> 5000+</label>
                    </div>

                </div>
                <div className="listing-properties">
                    {filteredProperties.map((property)=>( <PropertyCard key={property.id} property={property} large/>))}
=======
                            <input
                                type="radio"
                                name="price"
                                value="all"
                                checked={priceFilter === "all"}
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> All
                        </label>

                        <label>
                            <input
                                type="radio"
                                name="price"
                                value="low"
                                checked={priceFilter === "low"}
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> below 3000
                        </label>

                        <label>
                            <input
                                type="radio"
                                name="price"
                                value="mid"
                                checked={priceFilter === "mid"}
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> 3000 - 5000
                        </label>

                        <label>
                            <input
                                type="radio"
                                name="price"
                                value="high"
                                checked={priceFilter === "high"}
                                onChange={(e) => setPriceFilter(e.target.value)}
                            /> 5000+
                        </label>
                    </div>
                </div>

                <div className="listing-properties">
                    {filteredProperties.map((property) => (
                        <PropertyCard
                            key={property.id}
                            property={property}
                            large
                        />
                    ))}
>>>>>>> 8930ca1b81e7fa098e07f23bd308a156a6d3ce7b
                </div>
            </section>
        </div>
    );
}

export default Listings;