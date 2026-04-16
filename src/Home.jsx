import PropertyCard from "./Components/PropertyCard";   
import { Link } from "react-router-dom";

const properties = [
  {
    id: 1,
    name: "Cozy Boarding House",
    location: "Cebu City",
    price: 3500,
    image: "/assets/house1.jpg"
  },
  {
    id: 2,
    name: "Student Friendly Room",
    location: "Lahug",
    price: 2800,
    image: "/assets/house2.jpg"
  },
  {
    id: 3,
    name: "City Center Boarding",
    location: "IT Park",
    price: 4200,
    image: "/assets/house3.jpg"
  }
];

function Home() {
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
                        <input type="button" value="view All"></input>
                    </div>
                </div>

                <div className="featured-cards">
                    {properties.map((property)=>( <PropertyCard key={property.id} property={property} />))}
                </div>
            </section>

            <section className="sec-3">
                <h1>Ready to Find Your New Home?</h1>
                <p>Join the thousands of happy renters who found their perfect place with HomeSeek</p>
                <div className="browse-links">
                    <Link to="/listings" className="btn">Browse Listings</Link>
                    <Link to="/ListProperty" className="btn btn-outline">List Your Property</Link>
                </div>
            </section>
        </div>
    );

}

export default Home;