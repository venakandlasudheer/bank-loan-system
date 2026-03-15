# Bank Loan Management System

A full-stack Bank Loan Management System built with Java Spring Boot Microservices.

## Tech Stack
- Java 17
- Spring Boot 3.3.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL
- HTML, CSS, JavaScript

## Microservices
- **User Service** (port 8080) - Registration, Login, JWT Auth
- **Loan Service** (port 8081) - Loan Application, Admin Management

## Features
- User Registration and Login with JWT
- Role Based Access Control (USER/ADMIN)
- Apply for Loan
- Admin Approve/Reject Loans
- Responsive Frontend Dashboard

## How to Run
1. Create MySQL databases: userdb and loandb
2. Update application.properties with your MySQL password
3. Run user-service on port 8080
4. Run loan-service on port 8081
5. Open frontend/index.html in browser
