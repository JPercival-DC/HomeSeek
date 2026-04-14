import "./navbar.css";


function ListingNav(){
    return(
    <nav className="filter-nav">
      <input type="text" value="Search"/>
      <div className="filter-btns">
        <input type="button" value="Location" />  
        <input type="button" value="Property Type" />
        <input type="button" value="Bedrooms" />
        <input type="button" value="Price Range" />
        <input type="button" value="Search" />
      </div>
    </nav>
    );
}

export default ListingNav;