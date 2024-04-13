# E-Commerce-website-Bd
Built a simple online shopping system, using spring microservices architecture pattern.


# Overview
This project implements a robust microservices architecture using the Spring framework, incorporating key features for scalable and efficient service communication.

# Services
1. Inventory Service: Manages inventory levels and stock information.
2. Notification Service: Handles notifications and alerts for various events.
3. Order Service: Manages order processing, including creation and fulfillment.
4. Product Service: Maintains information about available products.

# Architecture Features
Event Driven Architecture
Utilizes an event-driven approach for seamless communication between microservices. Events are broadcasted and consumed asynchronously, ensuring decoupling and scalability.

# Centralized Logging
Employs centralized logging to streamline monitoring and debugging. Log data from all services is aggregated, providing a unified view of system activities.

# Circuit Breaker
Implements a circuit breaker pattern to enhance system resilience. This prevents cascading failures and allows graceful degradation in the face of service outages.

# Securing Microservices with Keycloak
Ensures robust security by integrating Keycloak, an open-source identity and access management solution. Microservices are secured, and user authentication and authorization are centralized.

# Technologies Used
* Spring Boot
* Spring Cloud
* Keycloak
* RabbitMQ
* ELK Stack