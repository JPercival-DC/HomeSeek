import { Navigate } from "react-router-dom";
import AdminDashboard from './AdminDashboard';

function ProtectedRoute({ children, allowedRoles }) {
    const userJson = localStorage.getItem("user");
    console.log("ProtectedRoute - Raw user from storage:", userJson);
    
    let user = null;
    try {
        user = JSON.parse(userJson);
        console.log("ProtectedRoute - Parsed user:", user);
        console.log("ProtectedRoute - User role:", user?.role);
        console.log("ProtectedRoute - Allowed roles:", allowedRoles);
    } catch (e) {
        console.error("ProtectedRoute - Error parsing user:", e);
    }
    
    // Check if user is logged in
    if (!user) {
        console.log("ProtectedRoute - No user found, redirecting to login");
        return <Navigate to="/login" replace />;
    }
    
    // Check if user has required role
    if (allowedRoles && allowedRoles.length > 0) {
        const hasRequiredRole = allowedRoles.includes(user.role);
        console.log("ProtectedRoute - Has required role?", hasRequiredRole);
        
        if (!hasRequiredRole) {
            console.log("ProtectedRoute - User role not allowed, redirecting to home");
            return <Navigate to="/" replace />;
        }
    }

    // Make role comparison case-insensitive
    if (allowedRoles && allowedRoles.length > 0) {
        const userRole = user.role?.toLowerCase();
        const hasRequiredRole = allowedRoles.some(role => role.toLowerCase() === userRole);
        
        if (!hasRequiredRole) {
            return <Navigate to="/" replace />;
        }
    }
    
    console.log("ProtectedRoute - Access granted");
    return children;

    
}

export default ProtectedRoute;