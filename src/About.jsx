import { Link } from "react-router-dom";
import "./styles/About.css";

function About() {
    return(
        <div>
            <section className="hero-v2">
                <h1>HomeSeek</h1>
                <p>Your Perfect Home Awaits</p>
                <h2>About Us</h2>
                <p>Your one-stop shop for finding the perfect home.
                    Coming soon.</p>
                    
                <p>
                    lorem ipsum, dolor sit amet consectetur adipisicing elit. Natus, laborum voluptatem ab recusandae sed repellat quaerat dolorum, nisi officia quis consequuntur omnis repudiandae voluptates accusantium at doloremque perferendis fugiat maxime? lorem ipsum, dolor sit amet consectetur adipisicing elit. Natus, laborum voluptatem ab recusandae sed repellat quaerat dolorum, nisi officia quis consequuntur omnis repudiandae voluptates accusantium at doloremque perferendis fugiat maxime?
                </p>
                
                {/* Add a link back to home */}
                <Link to="/" className="back-home">Back to Home</Link>
            </section>
        </div>
    );
}

export default About;