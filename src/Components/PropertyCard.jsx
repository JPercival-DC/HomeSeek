import { Link } from "react-router-dom";
import "./PropertyCard.css";
import PropertyDetails from "../PropertyDetails";

<<<<<<< HEAD
=======

>>>>>>> 8930ca1b81e7fa098e07f23bd308a156a6d3ce7b
function PropertyCard({ property, large }) {
  return (
    <div className={`property-card ${large ? "property-card-large" : ""}`}>
      <img src={property.image} alt={property.name} />
      <div className="property-info">
        <h3>{property.name}</h3>
        <div className={`property-info ${large ? "property-info-large" : ""}`}>
          <p className="location">{property.location}</p>
          <p className="price">₱{property.price}/mo</p>
        </div>
<<<<<<< HEAD
        <Link to="/PropertyDetails">View Details</Link>
=======
        <div className="link-button">
        <Link className="detail-link" to="/PropertyDetails">View Details</Link></div>
>>>>>>> 8930ca1b81e7fa098e07f23bd308a156a6d3ce7b
      </div>
    </div>
  );
}


export default PropertyCard;