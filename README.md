# School Leavers CRUD System

A Spring Boot application for managing school leavers data with user authentication.

## Project Overview

This application provides a simple CRUD (Create, Read, Update, Delete) interface for managing school leavers statistics data. It includes user authentication and form validation.

## Features

- User authentication (Username: CCT1234, Password: 54321)
- CRUD operations for school leavers data
- Form validation (backend)
- Modern web interface
- MySQL database integration

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL
- **Frontend**: Thymeleaf templates
- **Security**: Spring Security
- **Build Tool**: Maven

## Database Schema

### Users Table
- id (Primary Key)
- username (Unique)
- password
- enabled

### School Leavers Table
- id (Primary Key)
- statistic_code
- statistic_label
- quarter
- sex
- unit
- value

## Setup Instructions

1. Ensure MySQL is running with XAMPP
2. Create database: `school_leavers_db`
3. Import the provided SQL dump file
4. Update database connection in `application.properties`
5. Run the application: `mvn spring-boot:run`
6. Access the application at: `http://localhost:8080`

## Project Structure

```
src/
├── main/
│   ├── java/com/cct/schoolleavers/
│   │   ├── controllers/
│   │   ├── entities/
│   │   ├── repositories/
│   │   ├── services/
│   │   └── config/
│   ├── resources/
│   │   ├── templates/
│   │   ├── static/
│   │   └── application.properties
│   └── webapp/
└── test/
```

## Login Credentials

- **Username**: CCT1234
- **Password**: 54321 