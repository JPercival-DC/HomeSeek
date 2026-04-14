import "./PropertyCard.css";

function PropertyCard({ property }) {
  return (
    <div className="property-card">
      <img src={property.image} alt={property.name} />
      <div className="property-info">
        <h3>{property.name}</h3>
        <div className="price-loc">
          <p1 className="price">₱{property.price}/mo</p1>
          <p1 className="location">{property.location}</p1>
        </div>
      </div>
    </div>
  );
}

export default PropertyCard;