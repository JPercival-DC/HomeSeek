import "./PropertyCard.css";

function PropertyCard({ property }) {
  return (
    <div className="property-card">
      <img src={property.image} alt={property.name} />

      <h3>{property.name}</h3>
      <p>{property.location}</p>
      <p>₱{property.price} / month</p>
    </div>
  );
}

export default PropertyCard;