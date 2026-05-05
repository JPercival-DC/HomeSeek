import { Link } from "react-router-dom";
import "./styles/Follow.css";

function Follow() {
    return(
        <div className="follow-page">
            <section className="follow-hero">
                <h1>Stay Connected.</h1>
                <p className="subtitle">Follow our Social Media page to keep up with the latest updates and exciting promos!</p>

                <div className="social-links">
                    <a href="https://www.facebook.com/homeseek" className="social-card facebook" target="_blank" rel="noopener noreferrer">
                        <span className="social-icon">📘</span>
                        <div className="social-info">
                            <h3>Facebook</h3>
                            <p>/homeseek</p>
                        </div>
                        <span className="arrow">→</span>
                    </a>
                    
                    <a href="https://www.twitter.com/homeseek" className="social-card twitter" target="_blank" rel="noopener noreferrer">
                        <span className="social-icon">🐦</span>
                        <div className="social-info">
                            <h3>Twitter</h3>
                            <p>@homeseek</p>
                        </div>
                        <span className="arrow">→</span>
                    </a>
                    
                    <a href="https://www.instagram.com/homeseek" className="social-card instagram" target="_blank" rel="noopener noreferrer">
                        <span className="social-icon">📷</span>
                        <div className="social-info">
                            <h3>Instagram</h3>
                            <p>@homeseek</p>
                        </div>
                        <span className="arrow">→</span>
                    </a>
                </div>

                <div className="coming-soon-badge">
                    <p>✨ More features coming soon! ✨</p>
                </div>

                <Link to="/" className="back-home">Back to Home</Link>
            </section>
        </div>
    );
}

export default Follow;