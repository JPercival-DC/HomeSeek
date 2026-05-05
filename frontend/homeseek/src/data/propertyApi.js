const BASE_URL = "http://localhost:8080/api/properties";

// GET all
export const getProperties = async () => {
    const res = await fetch(BASE_URL);
    return res.json();
};

// CREATE
export const addProperty = async (property) => {
    const res = await fetch(BASE_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(property)
    });
    return res.json();
};

// UPDATE
export const updateProperty = async (id, property) => {
    const res = await fetch(`${BASE_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(property)
    });
    return res.json();
};

// DELETE
export const deleteProperty = async (id) => {
    await fetch(`${BASE_URL}/${id}`, {
        method: "DELETE"
    });
};