import { Link } from "react-router-dom";

function ForgotPassword() {
    return(
        <div>
            <section className="hero-v2">
                <h1>Forgot Password</h1>
                <p>Enter your email address and we'll send you a link to reset your password.

                    Coming soon.
                </p>
                <div>
                   <Link to="/">Home</Link>
                </div>
            </section>
           </div>
    );
}
    export default ForgotPassword;