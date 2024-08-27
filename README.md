# Demo of Spring Boot application with Spring Security, JWT, Angular, Flyaway, PostgreSQL

![app](app1.png)
![app](app2.png)
![app](app3.png)
![app](app4.png)
![app](app5.png)
![app](app6.png)

Easiest way to run:
 - 1. With maven and docker installed from project's root folder run command:
 ```
 docker compose up
 ```
 It will create container and images for backend, frontend, postgresql and apply migrations
 - 2. Navigating to http://localhost:8080/api/health_check to check is api is working
 - 3. Navigating to http://localhost:4200/register to create a new user
 - 4. Navigate to http://localhost:4200/login to login
 - 5. Add books, reserve with different users