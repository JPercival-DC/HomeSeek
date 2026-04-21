import { Link } from "react-router-dom";
import "./PropertyCard.css";


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
        <div className="link-button">
        <Link className="detail-link" to="/PropertyDetails">View Details</Link></div>
      </div>
    </div>
  );
}


export default PropertyCard;