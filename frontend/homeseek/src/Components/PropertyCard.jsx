import { Link } from "react-router-dom";
import "./PropertyCard.css";

function PropertyCard({ property, large }) {
  return (
    <div className={`property-card ${large ? "property-card-large" : ""}`}>
      <img src="placeholder.jpg" alt={property.propertyName} />
      <div className="property-info">
        <h3>{property.propertyName}</h3>
        
        {large && (
          <div className="extra-info">
            <p className="description">{property.description}</p>
          </div>
        )}
        <div className="price-loc">
          <p className="location">{property.address}</p>
          <p className="price">₱{property.price}/mo</p>
        </div>


        <div className="link-button">
          <Link className="detail-link" to={`/PropertyDetails`}>
          {/*<Link className="detail-link" to={`/PropertyDetails/${property.propertyId}`}> */}
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
}


export default PropertyCard;