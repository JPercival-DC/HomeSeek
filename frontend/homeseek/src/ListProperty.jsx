import { useState, useEffect } from "react";
import "./styles/ListProperty.css";

function ListProperty() {
    const [form, setForm] = useState({
        propertyName: "",
        address: "",
        price: "",
        description: "",
        type: "",
        rooms: "",
        availabilityStatus: "Available",
        ownerId: "" // Add this
    });

    useEffect(() => {
        // Get current user from localStorage
        const user = JSON.parse(localStorage.getItem("user"));
        if (user) {
            setForm(prev => ({
                ...prev,
                ownerId: user.userId
            }));
        }
    }, []);

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    async function handleSubmit(e) {
        e.preventDefault();

        const user = JSON.parse(localStorage.getItem("user"));
        
        const newProperty = {
            propertyName: form.propertyName,
            address: form.address,
            price: Number(form.price),
            description: form.description,
            type: form.type,
            rooms: Number(form.rooms),
            availabilityStatus: form.availabilityStatus,
            ownerId: form.ownerId
        };

        try {
            const response = await fetch("http://localhost:8080/api/properties", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-User-Role": user.role,
                    "X-User-Id": user.userId
                },
                body: JSON.stringify(newProperty)
            });

            if (response.ok) {
                alert("Property Added Successfully!");
                setForm({
                    propertyName: "",
                    address: "",
                    price: "",
                    description: "",
                    type: "",
                    rooms: "",
                    availabilityStatus: "Available",
                    ownerId: user.userId
                });
            } else {
                const error = await response.json();
                alert(error.error || "Failed to add property");
            }
        } catch (error) {
            console.log(error);
            alert("Error connecting to server");
        }
    }

    return (
        <div className="add-property-page">
            <h2>Add Property</h2>

            <form className="property-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="propertyName"
                    placeholder="Property Name"
                    value={form.propertyName}
                    onChange={handleChange}
                    required
                />

                <input
                    type="text"
                    name="address"
                    placeholder="Address"
                    value={form.address}
                    onChange={handleChange}
                    required
                />

                <input
                    type="number"
                    name="price"
                    placeholder="Price"
                    value={form.price}
                    onChange={handleChange}
                    required
                />

                <input
                    type="text"
                    name="description"
                    placeholder="Description"
                    value={form.description}
                    onChange={handleChange}
                />

                <input
                    type="text"
                    name="type"
                    placeholder="Type (Boarding House, Room, etc.)"
                    value={form.type}
                    onChange={handleChange}
                />

                <input
                    type="number"
                    name="rooms"
                    placeholder="Rooms"
                    value={form.rooms}
                    onChange={handleChange}
                />

                <button className="submit-btn" type="submit">
                    Submit Property
                </button>
            </form>
        </div>
    );
}

export default ListProperty;