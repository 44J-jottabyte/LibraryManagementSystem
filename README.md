# Library Management System

## Overview
The **Library Management System** is a Spring Boot application designed to manage library operations efficiently. It includes features for user registration, book management, and book loan/return processes. The system supports role-based functionality for librarians and members.

---

## Features
1. **User Management**:
   - User registration and authentication with role-based access.
   - JWT-based token authentication.

2. **Book Management**:
   - CRUD operations for books.
   - Search and filter by book details.

3. **Loan Management**:
   - Borrow and return books.
   - Track loan statuses (BORROWED, RETURNED).

4. **Role-Based Access**:
   - Librarians: Manage books and view loans.
   - Members: Borrow and return books.

---

## Technologies Used
- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Database**: Oracle
- **Build Tool**: Gradle
- **Authentication**: JWT (JSON Web Token)
- **Testing**: JUnit, Mockito
- **API Documentation**: Swagger, Postman

---

## Prerequisites
1. **Java**: Version 17 or later.
2. **Gradle**: Version 7.6 or later.
3. **Oracle Database**: Ensure Oracle is installed and running.
4. **IDE**: IntelliJ IDEA or Eclipse.

---

## Setup and Installation
1. Clone the repository:
   ```bash
   git clone https://gitlab.com/elgynkhalilzade/library-management-system.git
