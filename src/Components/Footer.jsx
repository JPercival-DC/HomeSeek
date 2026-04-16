import { Link } from "react-router-dom";
import "./Footer.css";
import homeSeekLogo from "../Assets/homeSeek_logo.png";

function Footer() {
  return (
    <footer className="footer-container">
      <ul className="footer-links">
        <li className="brand-item">
          <img src={homeSeekLogo} alt="HomeSeek Logo" className="footer-logo" />
          <h2>HomeSeek</h2>
        </li>
        <li><Link to="/About">About Us</Link></li>
        <li><Link to="/Contact">Contact</Link></li>
        <li><Link to="/follow">Follow Us</Link></li>
      </ul>
      <br/>
      <div className="footer-bottom">
        <p>© 2026 HomeSeek. All rights reserved.</p>
      </div>
    </footer>
  );
}

export default Footer;