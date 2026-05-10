import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import "./navbar.css";
import homeSeekLogo from "../Assets/homeSeek_logo.png";

function Navbar1() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [menuOpen, setMenuOpen] = useState(false);
    const menuRef = useRef(null);

    useEffect(() => {
        const checkUser = () => {
            const storedUser = localStorage.getItem("user");
            setUser(storedUser ? JSON.parse(storedUser) : null);
            setMenuOpen(false);
        };

        checkUser();
        window.addEventListener("storage", checkUser);

        return () => {
            window.removeEventListener("storage", checkUser);
        };
    }, []);

    // Close dropdown when clicking outside
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (menuRef.current && !menuRef.current.contains(event.target)) {
                setMenuOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    const handleLogout = () => {
        localStorage.removeItem("user");
        setUser(null);
        setMenuOpen(false);
        navigate("/login");
    };

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    // Check if user can list properties
    const canListProperty = user && (user.role === "owner" || user.role === "admin");

    return (
        <nav className="nav-container">
            <div className="nav-brand" onClick={() => navigate("/")}>
                <img src={homeSeekLogo} alt="HomeSeek Logo" className="nav-logo" />
                <h2 className="brand-text">HomeSeek</h2>
            </div>

            <ul className="nav-links">
                <li><Link to="/" className="nav-link">Home</Link></li>
                <li><Link to="/listings" className="nav-link">Listings</Link></li>

                {/* Show List Property link in main nav for owners/admins (optional) */}
                {canListProperty && (
                    <li><Link to="/ListProperty" className="nav-link">List Property</Link></li>
                )}

                {/* AUTH BASED UI */}
                {!user ? (
                    <>
                        <li><Link to="/login" className="nav-link">Login</Link></li>
                        <li><Link to="/register" className="nav-link">Register</Link></li>
                    </>
                ) : (
                    <li className="menu-container" ref={menuRef}>
                        <button className="nav-link" onClick={toggleMenu}>
                            Menu ▾
                        </button>

                        {menuOpen && (
                            <div className="dropdown-menu">
                                <Link to="/profile" className="dropdown-item" onClick={() => setMenuOpen(false)}>
                                    👤 Profile
                                </Link>

                                {/* Show Admin Dashboard only for admin role */}
                                {user && user.role === "admin" && (
                                    <Link to="/admin" className="dropdown-item" onClick={() => setMenuOpen(false)}>
                                        ⚙️ Admin Dashboard
                                    </Link>
                                )}

                                {/* Show List Property for owner or admin */}
                                {canListProperty && (
                                    <Link to="/ListProperty" className="dropdown-item" onClick={() => setMenuOpen(false)}>
                                        📝 List Property
                                    </Link>
                                )}

                                <button onClick={handleLogout} className="dropdown-item logout">
                                    🚪 Logout
                                </button>
                            </div>
                        )}
                    </li>
                )}
            </ul>

            <div className="nav-mobile-menu">
                <button className="mobile-menu-btn">☰</button>
            </div>
        </nav>
    );
}

export default Navbar1;