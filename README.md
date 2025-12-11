# Flight Reservation System

A simple Flight Reservation System implemented in Java 17 using Spring Boot.  
The system allows users to:

- Search for available flights by destination and date
- Book seats on a flight
- View all reservations for a given customer

This implementation simulates a real-world backend service that a Java Engineer might build for an airline or travel platform.

---

## Tech Stack

- **Language:** Java 17  
- **Framework:** Spring Boot  
- **Modules:** Spring Web, Spring Data JPA  
- **Database:** H2 in-memory database (no external setup required)  
- **Build Tool:** Maven  
- **Testing:** JUnit 5, Spring Boot Test

---

## How to Run the Application

### Prerequisites

- Java 17 installed  
- Maven installed

### 1. Clone the Repository

```bash
git clone https://github.com/yrlmanoharreddy/Flight
cd Flight
mvn spring-boot:run
or 
go to the main file and execute



## REST API Endpoints

Although the original requirement mentions a console-based interface, this implementation exposes the core functionality via a simple REST API.  
A console/CLI client can easily be layered on top of these endpoints if needed.

---

### 1. List All Flights (debug/demo)

**Request**

```http
GET /api/flights

