import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import "./styles/Register.css";

function Register() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const { name, email, password, confirmPassword } = formData;

        if (password !== confirmPassword) {
            alert("Passwords don't match!");
            return;
        }

        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/api/users/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    name,
                    email,
                    password
                })
            });

            if (!response.ok) {
                throw new Error("Failed to register user");
            }

            const data = await response.json();

            console.log("Server response:", data);

            alert("Registration successful!");

            // redirect to login after success
            navigate("/login");

        } catch (error) {
            console.error(error);
            alert("Registration failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-container">
            <div className="register-card">

                <div className="register-header">
                    <h1>Create Account</h1>
                    <p>Join us today! It's free and easy</p>
                </div>

                <form onSubmit={handleSubmit} className="register-form">

                    <div className="form-group">
                        <label>Full Name</label>
                        <input
                            type="text"
                            id="name"
                            value={formData.name}
                            onChange={handleChange}
                            placeholder="John Doe"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Email Address</label>
                        <input
                            type="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                            placeholder="you@example.com"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            id="password"
                            value={formData.password}
                            onChange={handleChange}
                            placeholder="Create a password"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            placeholder="Confirm your password"
                            required
                        />
                    </div>

                    <button type="submit" className="register-btn" disabled={loading}>
                        {loading ? "Registering..." : "Register"}
                    </button>

                </form>

                <div className="register-footer">
                    <p>
                        Already have an account?{" "}
                        <Link to="/login" className="login-link">
                            Login here
                        </Link>
                    </p>
                </div>

            </div>
        </div>
    );
}

export default Register;