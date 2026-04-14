import PropertyCard from "./Components/PropertyCard";   
import ListingNav from "./navbar/listing-nav";

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
    name: "Student Room",
    location: "Lahug",
    price: 2800,
    image: "/assets/house2.jpg"
  },
  {
    id: 3,
    name: "City Boarding",
    location: "IT Park",
    price: 4200,
    image: "/assets/house3.jpg"
  },

  {
    id: 4,
    name: "Lahug Boarding House",
    location: "lahug",
    price: 4500,
    image: "/assets/house3.jpg"
  },

  {
    id: 5,
    name: "IT City Boarding",
    location: "IT Park",
    price: 5200,
    image: "/assets/house3.jpg"
  },
  {
    id: 6,
    name: "CS Boarding",
    location: "Lahug",
    price: 4100,
    image: "/assets/house3.jpg"
  }
];

function Listings() {
    return (
        <div>
            <section className="hero-v2">
                <h1>Seek your Perfect Home</h1>
            </section>

            <section className="featured">
                <h2>Properties</h2>
                <div className="filter-container">
                    <ListingNav/>
                </div>
                <div>
                    {properties.map((property)=>( <PropertyCard key={property.id} property={property} />))}
                </div>
            </section>
           </div>
    );
}

export default Listings;