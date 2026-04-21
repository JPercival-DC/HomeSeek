import PropertyCard from "./Components/PropertyCard";   
import { Link } from "react-router-dom";
import ListingNav from "./navbar/listing-nav";
import properties from "./data/properties";

function Home() {
    return (
        <div>
            <section className="hero">
                <h1>Seek your Perfect Home</h1>
                <ListingNav/>
            </section>

            <section className="featured">

                <div className="featured-top">

                    <div className="featured-header">
                        <h2>Featured Listing</h2>
                        <p>Hand-picked apartments just for you</p>
                    </div>

                    <div className="featured-button">
                        <input type="button" value="view All"></input>
                    </div>
                </div>

                <div className="featured-cards">
                    {properties.filter((property)=>property.featured).map((property)=>( <PropertyCard key={property.id} property={property} />))}
                </div>
            </section>

            <section className="sec-3">
                <h1>Ready to Find Your New Home?</h1>
                <p>Join the thousands of happy renters who found their perfect place with HomeSeek</p>
                <ul className="browse-links">
                    <li><Link to="/listings">Browse Listings</Link></li>
                    <li><Link to="/ListProperty">List Your Property</Link></li>
                </ul>
            </section>
        </div>
    );

}

export default Home;