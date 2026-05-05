import { Link } from "react-router-dom";
import "./navbar.css";
import homeSeekLogo from "../Assets/homeSeek_logo.png"; // Import the logo

function Navbar1() {
  return (
    <nav className="nav-container">
      <div className="nav-brand">
        <img src={homeSeekLogo} alt="HomeSeek Logo" className="nav-logo" />
        <h2 className="brand-text">HomeSeek</h2>
      </div>

      <ul className="nav-links">
        <li><Link to="/" className="nav-link">Home</Link></li>
        <li><Link to="/listings" className="nav-link">Listings</Link></li>
        <li><Link to="/login" className="nav-link">Login</Link></li>
        <li><Link to="/register" className="nav-link">Register</Link></li>
        <li><Link to="/profile" className="nav-link">Profile</Link></li>
      </ul>

      <div className="nav-mobile-menu">
        <button className="mobile-menu-btn">☰</button>
      </div>
    </nav>
  );
}

export default Navbar1;