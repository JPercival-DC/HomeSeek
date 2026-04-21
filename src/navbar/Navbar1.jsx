import { Link } from "react-router-dom";
import "./navbar.css";

function Navbar1() {
  return (
    <nav className="nav-container">
      <h2>HomeSeek</h2>

      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/listings">Listings</Link></li>
        <li><Link to="/login">Login</Link></li>
        <li><Link to="/register">Register</Link></li>
        <li><Link to="/profile">Profile</Link></li>
      </ul>
    </nav>
  );
}

export default Navbar1;