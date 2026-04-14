import "./navbar.css";


function ListingNav(){
    return(
    <nav className="filter-nav">
      <h2>filters</h2>

      <input type="button" value="Location" />  
      <input type="button" value="Property Type" />
      <input type="button" value="Bedrooms" />
      <input type="button" value="Price Range" />
      <input type="button" value="Search" />
    </nav>
    );
}

export default ListingNav;