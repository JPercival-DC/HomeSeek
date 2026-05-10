import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import "./styles/Login.css";

function Login() {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/api/users/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email,
                    password
                })
            });

            const data = await response.json();
            console.log("Login response:", data);
            console.log("User role from server:", data.role);

            if (!response.ok) {
                throw new Error(data.message || "Login failed");
            }

            // Make sure role is stored correctly
            if (data.role) {
                // Ensure role is lowercase
                data.role = data.role.toLowerCase();
            }
            
            localStorage.setItem("user", JSON.stringify(data));
            
            // Verify it was stored correctly
            const stored = JSON.parse(localStorage.getItem("user"));
            console.log("Stored user:", stored);
            console.log("Stored role:", stored.role);

            window.dispatchEvent(new Event("storage"));
            navigate("/");

        } catch (error) {
            console.error("LOGIN ERROR:", error.message);
            alert(error.message || "Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">

                <div className="login-header">
                    <h1>Welcome Back</h1>
                    <p>Please login to your account</p>
                </div>

                <form onSubmit={handleSubmit} className="login-form">

                    <div className="form-group">
                        <label htmlFor="email">Email Address</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="you@example.com"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                            required
                        />
                    </div>

                    <button type="submit" className="login-btn" disabled={loading}>
                        {loading ? "Logging in..." : "Login"}
                    </button>

                </form>

                <div className="login-footer">
                    <p>
                        Don't have an account?{" "}
                        <Link to="/register" className="register-link">
                            Register here
                        </Link>
                    </p>
                </div>

            </div>
        </div>
    );
}

export default Login;