import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./styles/AdminDashboard.css";

function AdminDashboard() {
    const navigate = useNavigate();
    const [admins, setAdmins] = useState([]);
    const [users, setUsers] = useState([]);
    const [tenants, setTenants] = useState([]);
    const [owners, setOwners] = useState([]);
    const [properties, setProperties] = useState([]);  // NEW
    const [statistics, setStatistics] = useState({
        totalUsers: 0,
        totalAdmins: 0,
        totalOwners: 0,
        totalTenants: 0
    });
    const [activeTab, setActiveTab] = useState("dashboard");
    const [loading, setLoading] = useState(true);
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [selectedAdmin, setSelectedAdmin] = useState(null);
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        phone: ""
    });

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem("user"));
        if (!user || user.role !== "admin") {
            navigate("/login");
        }
    }, [navigate]);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setLoading(true);
        try {
            const statsRes = await fetch("http://localhost:8080/api/admin/statistics");
            const statsData = await statsRes.json();
            setStatistics(statsData);

            const adminsRes = await fetch("http://localhost:8080/api/admin/all");
            const adminsData = await adminsRes.json();
            setAdmins(adminsData);

            const usersRes = await fetch("http://localhost:8080/api/admin/users");
            const usersData = await usersRes.json();
            setUsers(usersData);

            const tenantsRes = await fetch("http://localhost:8080/api/admin/tenants");
            const tenantsData = await tenantsRes.json();
            setTenants(tenantsData);

            const ownersRes = await fetch("http://localhost:8080/api/admin/owners");
            const ownersData = await ownersRes.json();
            setOwners(ownersData);

            // NEW: Fetch all properties (admin sees everything including Pending)
            const propsRes = await fetch("http://localhost:8080/api/properties", {
                headers: { "X-User-Role": "admin" }
            });
            const propsData = await propsRes.json();
            setProperties(propsData);

        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setLoading(false);
        }
    };

    // NEW: Approve or reject a property
    const handlePropertyStatus = async (id, newStatus) => {
        try {
            const response = await fetch(`http://localhost:8080/api/properties/${id}/status`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "X-User-Role": "admin"
                },
                body: JSON.stringify({ availabilityStatus: newStatus })
            });
            if (response.ok) {
                alert(`Property ${newStatus === "Available" ? "approved" : "rejected"} successfully!`);
                fetchData();
            } else {
                alert("Failed to update property status");
            }
        } catch (error) {
            console.error("Error updating property status:", error);
            alert("Error connecting to server");
        }
    };

    const handleCreateAdmin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/api/admin/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                alert("Admin created successfully!");
                setShowCreateModal(false);
                setFormData({ name: "", email: "", password: "", phone: "" });
                fetchData();
            } else {
                const error = await response.json();
                alert(error.error || "Failed to create admin");
            }
        } catch (error) {
            console.error("Error creating admin:", error);
            alert("Error creating admin");
        }
    };

    const handleUpdateAdmin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/admin/update/${selectedAdmin.userId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(selectedAdmin)
            });
            if (response.ok) {
                alert("Admin updated successfully!");
                setShowEditModal(false);
                fetchData();
            } else {
                alert("Failed to update admin");
            }
        } catch (error) {
            console.error("Error updating admin:", error);
            alert("Error updating admin");
        }
    };

    const handleDeleteAdmin = async (id) => {
        if (window.confirm("Are you sure you want to delete this admin?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/admin/delete/${id}`, {
                    method: "DELETE"
                });
                if (response.ok) {
                    alert("Admin deleted successfully!");
                    fetchData();
                } else {
                    alert("Failed to delete admin");
                }
            } catch (error) {
                console.error("Error deleting admin:", error);
                alert("Error deleting admin");
            }
        }
    };

    const StatCard = ({ title, value, color }) => (
        <div className={`stat-card stat-card-${color}`}>
            <h3>{title}</h3>
            <p className="stat-value">{value}</p>
        </div>
    );

    // NEW: Badge color per status
    const statusBadge = (status) => (
        <span className={`role-badge role-${status.toLowerCase()}`}>{status}</span>
    );

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    const pendingCount = properties.filter(p => p.availabilityStatus === "Pending").length;

    return (
        <div className="admin-dashboard">
            <nav className="admin-nav">
                <div className="nav-brand">
                    <h2>HomeSeek Admin Panel</h2>
                </div>
                <div className="nav-links">
                    <button onClick={() => setActiveTab("dashboard")}>Dashboard</button>
                    <button onClick={() => setActiveTab("admins")}>Admins</button>
                    <button onClick={() => setActiveTab("users")}>All Users</button>
                    <button onClick={() => setActiveTab("tenants")}>Tenants</button>
                    <button onClick={() => setActiveTab("owners")}>Owners</button>
                    {/* NEW: Shows pending count badge */}
                    <button onClick={() => setActiveTab("properties")}>
                        Properties {pendingCount > 0 && <span className="pending-badge">{pendingCount}</span>}
                    </button>
                </div>
            </nav>

            <div className="admin-content">
                {activeTab === "dashboard" && (
                    <div className="dashboard-tab">
                        <h1>Dashboard Overview</h1>
                        <div className="stats-grid">
                            <StatCard title="Total Users" value={statistics.totalUsers} color="blue" />
                            <StatCard title="Total Admins" value={statistics.totalAdmins} color="green" />
                            <StatCard title="Total Owners" value={statistics.totalOwners} color="orange" />
                            <StatCard title="Total Tenants" value={statistics.totalTenants} color="purple" />
                        </div>
                        
                        <div className="recent-activities">
                            <h2>Recent Admins</h2>
                            <table className="data-table">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {admins.slice(0, 5).map(admin => (
                                        <tr key={admin.userId}>
                                            <td>{admin.name}</td>
                                            <td>{admin.email}</td>
                                            <td>{admin.phone || "N/A"}</td>
                                            <td>
                                                <button 
                                                    className="btn-edit"
                                                    onClick={() => {
                                                        setSelectedAdmin(admin);
                                                        setShowEditModal(true);
                                                    }}
                                                >Edit</button>
                                                <button 
                                                    className="btn-delete"
                                                    onClick={() => handleDeleteAdmin(admin.userId)}
                                                >Delete</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                )}

                {activeTab === "admins" && (
                    <div className="admins-tab">
                        <div className="tab-header">
                            <h1>Admin Management</h1>
                            <button className="btn-create" onClick={() => setShowCreateModal(true)}>
                                + Create New Admin
                            </button>
                        </div>
                        <table className="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {admins.map(admin => (
                                    <tr key={admin.userId}>
                                        <td>{admin.userId}</td>
                                        <td>{admin.name}</td>
                                        <td>{admin.email}</td>
                                        <td>{admin.phone || "N/A"}</td>
                                        <td>
                                            <button 
                                                className="btn-edit"
                                                onClick={() => {
                                                    setSelectedAdmin(admin);
                                                    setShowEditModal(true);
                                                }}
                                            >Edit</button>
                                            <button 
                                                className="btn-delete"
                                                onClick={() => handleDeleteAdmin(admin.userId)}
                                            >Delete</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

                {activeTab === "users" && (
                    <div className="users-tab">
                        <h1>All Users</h1>
                        <table className="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map(user => (
                                    <tr key={user.userId}>
                                        <td>{user.userId}</td>
                                        <td>{user.name}</td>
                                        <td>{user.email}</td>
                                        <td><span className={`role-badge role-${user.role.toLowerCase()}`}>{user.role}</span></td>
                                        <td>{user.phone || "N/A"}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

                {activeTab === "tenants" && (
                    <div className="tenants-tab">
                        <h1>Tenants</h1>
                        <table className="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>
                            <tbody>
                                {tenants.map(tenant => (
                                    <tr key={tenant.userId}>
                                        <td>{tenant.userId}</td>
                                        <td>{tenant.name}</td>
                                        <td>{tenant.email}</td>
                                        <td>{tenant.phone || "N/A"}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

                {activeTab === "owners" && (
                    <div className="owners-tab">
                        <h1>Property Owners</h1>
                        <table className="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>
                            <tbody>
                                {owners.map(owner => (
                                    <tr key={owner.userId}>
                                        <td>{owner.userId}</td>
                                        <td>{owner.name}</td>
                                        <td>{owner.email}</td>
                                        <td>{owner.phone || "N/A"}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

                {/* NEW: Properties approval tab */}
                {activeTab === "properties" && (
                    <div className="properties-tab">
                        <h1>Property Listings</h1>
                        <table className="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Address</th>
                                    <th>Type</th>
                                    <th>Price</th>
                                    <th>Rooms</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {properties.map(property => (
                                    <tr key={property.propertyId}>
                                        <td>{property.propertyId}</td>
                                        <td>{property.propertyName}</td>
                                        <td>{property.address}</td>
                                        <td>{property.type || "N/A"}</td>
                                        <td>₱{property.price?.toLocaleString()}</td>
                                        <td>{property.rooms || "N/A"}</td>
                                        <td>{statusBadge(property.availabilityStatus)}</td>
                                        <td>
                                            {property.availabilityStatus === "Pending" && (
                                                <>
                                                    <button
                                                        className="btn-approve"
                                                        onClick={() => handlePropertyStatus(property.propertyId, "Available")}
                                                    >Approve</button>
                                                    <button
                                                        className="btn-delete"
                                                        onClick={() => handlePropertyStatus(property.propertyId, "Rejected")}
                                                    >Reject</button>
                                                </>
                                            )}
                                            {property.availabilityStatus === "Available" && (
                                                <button
                                                    className="btn-delete"
                                                    onClick={() => handlePropertyStatus(property.propertyId, "Rejected")}
                                                >Revoke</button>
                                            )}
                                            {property.availabilityStatus === "Rejected" && (
                                                <button
                                                    className="btn-approve"
                                                    onClick={() => handlePropertyStatus(property.propertyId, "Available")}
                                                >Re-approve</button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>

            {/* Create Admin Modal */}
            {showCreateModal && (
                <div className="modal-overlay" onClick={() => setShowCreateModal(false)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h2>Create New Admin</h2>
                        <form onSubmit={handleCreateAdmin}>
                            <input
                                type="text"
                                placeholder="Full Name"
                                value={formData.name}
                                onChange={(e) => setFormData({...formData, name: e.target.value})}
                                required
                            />
                            <input
                                type="email"
                                placeholder="Email"
                                value={formData.email}
                                onChange={(e) => setFormData({...formData, email: e.target.value})}
                                required
                            />
                            <input
                                type="password"
                                placeholder="Password"
                                value={formData.password}
                                onChange={(e) => setFormData({...formData, password: e.target.value})}
                                required
                            />
                            <input
                                type="tel"
                                placeholder="Phone Number"
                                value={formData.phone}
                                onChange={(e) => setFormData({...formData, phone: e.target.value})}
                            />
                            <div className="modal-buttons">
                                <button type="submit" className="btn-submit">Create</button>
                                <button type="button" className="btn-cancel" onClick={() => setShowCreateModal(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* Edit Admin Modal */}
            {showEditModal && selectedAdmin && (
                <div className="modal-overlay" onClick={() => setShowEditModal(false)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h2>Edit Admin</h2>
                        <form onSubmit={handleUpdateAdmin}>
                            <input
                                type="text"
                                placeholder="Full Name"
                                value={selectedAdmin.name}
                                onChange={(e) => setSelectedAdmin({...selectedAdmin, name: e.target.value})}
                                required
                            />
                            <input
                                type="email"
                                placeholder="Email"
                                value={selectedAdmin.email}
                                onChange={(e) => setSelectedAdmin({...selectedAdmin, email: e.target.value})}
                                required
                            />
                            <input
                                type="password"
                                placeholder="New Password (leave blank to keep current)"
                                onChange={(e) => setSelectedAdmin({...selectedAdmin, password: e.target.value})}
                            />
                            <input
                                type="tel"
                                placeholder="Phone Number"
                                value={selectedAdmin.phone || ""}
                                onChange={(e) => setSelectedAdmin({...selectedAdmin, phone: e.target.value})}
                            />
                            <div className="modal-buttons">
                                <button type="submit" className="btn-submit">Update</button>
                                <button type="button" className="btn-cancel" onClick={() => setShowEditModal(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default AdminDashboard;