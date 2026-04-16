import { Link } from "react-router-dom";
import "./styles/Contact.css";

function Contact() {
    return(
        <div className="contact-page">
            <section className="contact-hero">
                <h1>Have questions? Talk to us.</h1>
                <p>We love to hear from you! Don't hesitate to reach out.</p>
                
                <form className="contact-form">
                    <input type="text" placeholder="Your Name" className="form-input" />
                    <input type="email" placeholder="Your Email" className="form-input" />
                    <textarea placeholder="Your Message" className="form-textarea" rows="5"></textarea>
                    <button type="submit" className="submit-btn">Send Message</button>
                </form>
                
                <p className="email-link">Or email us at <a href="mailto:contact@homeseek.com">contact@homeseek.com</a></p>
                <Link to="/" className="back-home">Back to Home</Link>
            </section>
        </div>
    );
}

export default Contact;