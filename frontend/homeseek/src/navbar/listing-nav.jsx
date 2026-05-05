import { useState } from "react";
import "./listing-nav.css";

function ListingNav() {
  const [selectedLocation, setSelectedLocation] = useState("");
  const [selectedType, setSelectedType] = useState("");
  const [selectedBedrooms, setSelectedBedrooms] = useState("");
  const [priceRange, setPriceRange] = useState("");
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearch = () => {
    // Handle filter logic here
    console.log({
      location: selectedLocation,
      type: selectedType,
      bedrooms: selectedBedrooms,
      priceRange: priceRange,
      search: searchQuery
    });
  };

  const handleReset = () => {
    setSelectedLocation("");
    setSelectedType("");
    setSelectedBedrooms("");
    setPriceRange("");
    setSearchQuery("");
  };

  return (
    <div className="filter-container">
      <div className="filter-header">
        <h3>🔍 Filters</h3>
        <button onClick={handleReset} className="reset-btn">Reset All</button>
      </div>

      <div className="filter-controls">
        {/* Location Filter */}
        <select 
          className="filter-select"
          value={selectedLocation}
          onChange={(e) => setSelectedLocation(e.target.value)}
        >
          <option value="">All Locations</option>
          <option value="Cebu City">Cebu City</option>
          <option value="Lahug">Lahug</option>
          <option value="IT Park">IT Park</option>
          <option value="Mandaue">Mandaue</option>
        </select>

        {/* Property Type Filter */}
        <select 
          className="filter-select"
          value={selectedType}
          onChange={(e) => setSelectedType(e.target.value)}
        >
          <option value="">All Types</option>
          <option value="Boarding House">Boarding House</option>
          <option value="Student Room">Student Room</option>
          <option value="Apartment">Apartment</option>
          <option value="Studio">Studio</option>
        </select>

        {/* Bedrooms Filter */}
        <select 
          className="filter-select"
          value={selectedBedrooms}
          onChange={(e) => setSelectedBedrooms(e.target.value)}
        >
          <option value="">Any Bedrooms</option>
          <option value="1">1 Bedroom</option>
          <option value="2">2 Bedrooms</option>
          <option value="3">3 Bedrooms</option>
          <option value="4">4+ Bedrooms</option>
        </select>

        {/* Price Range Filter */}
        <select 
          className="filter-select"
          value={priceRange}
          onChange={(e) => setPriceRange(e.target.value)}
        >
          <option value="">Any Price</option>
          <option value="0-3000">₱0 - ₱3,000</option>
          <option value="3000-5000">₱3,000 - ₱5,000</option>
          <option value="5000-8000">₱5,000 - ₱8,000</option>
          <option value="8000+">₱8,000+</option>
        </select>

        {/* Search Input */}
        <div className="search-wrapper">
          <input 
            type="text" 
            className="search-input"
            placeholder="Search by name or location..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>

        {/* Action Buttons */}
        <button onClick={handleSearch} className="search-btn">
          Search
        </button>
      </div>
    </div>
  );
}

export default ListingNav;