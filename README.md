**Latest Update**

I’ve set up the full-stack structure of the project.

Latest:
Connected React frontend to Spring Boot backend
Created full CRUD for Property (Create, Read, Update, Delete) on spring Boot
Set up MySQL database (homeseek_db)
Updated backend with Entity, Repository, Service, and Controller (note: only Property awas created on Entity; still need User, Messages, and so on)
Connected React to backend API (no more localStorage)
Fixed routing and data display from database
**
Restructured whole project on GitHub**

**Project structure now:**
frontend/homeseek → React app (runs on http://localhost:3000)
backend/homeseeksystem → Spring Boot API (runs on http://localhost:8080)
MySQL handles all property data

Important notes:
Run backend first before React
Make sure MySQL is running
Database is local (not deployed; need to create homeseek_db first on mysql)
