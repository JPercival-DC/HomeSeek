import { Link } from "react-router-dom";

function Register() {
    return (
        <div>
            <section className="hero-v2">
                <h1>Register Here.</h1>
                <div>
                   <Link to="/Login">Go to Login</Link>
                </div>
            </section>
           </div>
    );
}

export default Register;