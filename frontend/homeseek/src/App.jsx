import Navbar from './navbar/Navbar1';
import Footer from './Components/Footer';
import './App.css';
import Home from './Home';
import Listings from './Listings';
import Login from './Login';
import Register from './Register';
import ListProperty from './ListProperty';
import About from './About';
import Contact from './Contact';
import Follow from './Follow';
import Profile from './Profile';
import ForgotPassword from './ForgotPassword';
import PropertyDetails from './PropertyDetails';
import AdminDashboard from './AdminDashboard';
import ProtectedRoute from './ProtectedRoute';
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Navbar/>
      <Routes>
        {/* Public Routes - Anyone can access */}
        <Route path="/" element={<Home />} />
        <Route path="/listings" element={<Listings />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} /> 
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/follow" element={<Follow />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/PropertyDetails" element={<PropertyDetails />} />
        
        {/* Protected Routes - Only authenticated users */}
        <Route path="/profile" element={
          <ProtectedRoute>
            <Profile />
          </ProtectedRoute>
        } />
        
        {/* List Property - Only owners and admins can access */}
        <Route path="/ListProperty" element={
          <ProtectedRoute allowedRoles={["owner", "admin"]}>
            <ListProperty />
          </ProtectedRoute>
        } />
        
        {/* Admin Dashboard - Only admins can access */}
        <Route path="/admin" element={
            <ProtectedRoute allowedRoles={["admin"]}>
                <AdminDashboard />
            </ProtectedRoute>
        } />
      </Routes>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;