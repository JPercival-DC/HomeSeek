import { useState } from "react";

function ListProperty() {
    const [form, setForm] = useState({
        name: "",
        location: "",
        price: "",
        image: ""
    });

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function handleSubmit(e) {
        e.preventDefault();

        const existing = JSON.parse(localStorage.getItem("properties")) || [];

        const newProperty = {
            id: Date.now(),
            name: form.name,
            location: form.location,
            price: Number(form.price),
            image: "/assets/house3.jpg",
        };

        const updated = [...existing, newProperty];

        localStorage.setItem("properties", JSON.stringify(updated));

        alert("Property Added!");
    }

    return (
        <div className="hero-v2">
            <h2>Add Property</h2>

            <form onSubmit={handleSubmit}>
                <input name="name" placeholder="Name" onChange={handleChange} />
                <input name="location" placeholder="Location" onChange={handleChange} />
                <input name="price" placeholder="Price" onChange={handleChange} />
                <button type="submit">Submit Property</button>
            </form>
        </div>
    );
}



export default ListProperty;