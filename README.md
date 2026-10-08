# Udemy Clone 🎓

A full-stack e-learning platform clone built using a modern decoupled architecture. The application allows teachers to create courses and manage curriculum lessons, while students can browse, enroll in courses, and track their progress. It features an automated revenue-split system between educators and the platform.

---

## 🚀 Tech Stack

### Backend
* **Java 21** & **Spring Boot 3.x**
* **Spring Security** (JWT Authentication & Authorization)
* **Spring Data JPA** (Hibernate ORM)
* **MySQL Database** (Relational data persistence)
* **Lombok** (Boilerplate reduction)

### Frontend
* **React 18** (TypeScript)
* **Tailwind CSS** (Modern UI styling with Indigo theme)
* **React Hook Form** (Form state management & validation)
* **Axios** (HTTP Client with JWT Interceptors)
* **React Router DOM v6** (Client-side routing)

---

## 🌟 Key Features

* **Role-Based Access Control:** Distinct workflows for **Students**, **Teachers**, and the **Site Owner** using JWT-secured endpoints.
* **Course Creation:** Teachers can publish new courses complete with images (`MultipartFile` processing), metadata, pricing, and nested curriculum lessons.
* **Interactive Curriculum Builder:** A clean split-screen UI built with Tailwind CSS allowing dynamic lesson injections into existing course IDs.
* **Automated Ledger Analytics:** Dynamic calculation logic processing course enrollment revenue splits (90% to the course Teacher, 10% platform share to the Site Owner).
* **Robust Global Error Handling:** Spring Boot application exceptions safely catch structural database drops or missing mappings to ensure smooth runtime UX.

---

## ⚙️ Architecture Design

The application passes cross-origin requests through a secure pipeline. The React client handles visual persistence layer mappings, dynamically translating properties across network boundaries to Spring Boot's immutably structured Java Record component types.
---

## 🛠️ Getting Started Local Setup

### Prerequisites
* Java JDK 21
* Node.js (v18 or higher)
* MySQL Server

### 1. Database Configuration
1. Open your MySQL client (e.g., MySQL Workbench) and create a schema named `udemy`:
   ```sql
   CREATE DATABASE udemy;
