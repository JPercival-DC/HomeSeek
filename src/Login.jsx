import { Link } from "react-router-dom";

function Login() {
    return (
        <div>
            <section className="hero-v2">
                <h1>Login Here.</h1>
                <Link to="/">Click to Login</Link>
                <p>Forgot your password? <Link to="/forgot-password">Click here</Link></p>
                <div>
                   <Link to="/Register">Go to Register</Link>
                </div>
            </section>
           </div>
    );
}

export default Login;