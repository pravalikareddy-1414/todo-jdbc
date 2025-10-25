# ToDo List Application (Spring Boot + JDBC + MySQL)

This is a simple **ToDo List REST API** built with **Spring Boot** and **MySQL** using plain JDBC (no ORM).  
It demonstrates how to connect a Spring Boot application to a database and perform CRUD operations.

---

## 🚀 Features
- Add new tasks with title, description, status, and priority  
- Update existing tasks  
- Fetch all tasks or a task by ID  
- Filter tasks by **status** or **priority**  
- Delete tasks  
- Database persistence using **JDBC** (no ORM frameworks)  

---

## 🛠️ Tech Stack
- Java 17  
- Spring Boot 3.x  
- MySQL 8.x  
- Maven  

---

## 📂 Project Structure
src/main/java/com/example/todo
├── Controller # REST controllers
├── Service # Business logic
├── Repository # JDBC repository (manual SQL queries)
├── Model # Task entity, enums (Status, Priority)



