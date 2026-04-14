function Contact() {
    return(
        <div>
            <section className="hero-v2">
                <h1>Have questions? Talk to us.</h1>
                <p>We love to hear from you! Don't hesitate to reach out.</p>
                <form>
                    <input type="text" placeholder="Your Name" />
                    <input type="email" placeholder="Your Email" />
                    <textarea placeholder="Your Message"></textarea>
                    <button>Send Message</button>
                </form>
                <p>Or email us at contact@homeseek.com</p>
                
            </section>
           </div>
    );
}

export default Contact;