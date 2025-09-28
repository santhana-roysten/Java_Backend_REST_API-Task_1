# Java Backend REST API

## Project Overview:
Spring Boot application providing REST API for task management with Mongo DB integration

## Technology and Tools:
- Java 25
- Spring Boot 3.5.6
- Mongo Atlas
- Maven
- Postman
- IntelliJ IDEA

## API Endpoints:
- PUT /api/tasks - Create a Task
- GET /api/tasks - Get all Tasks
- GET /api/tasks/search - Search Tasks by name and ID
- PUT /api/tasks/execute - Execute task command
- DELETE /api/tasks - Delete a task by ID

## Project Structure:
### src/main/java/com/example/taskmanager/
- |---- TaskManagerApplication.java
- |---- Task.java 
- |---- TaskExecution.java
- |---- TaskController.java
- |---- TaskRepository.java

## Testing
Application runs on: http://localhost:8080
### Applications runs successfully
![application runs]( api_testing_screenshots/connectionsuccess.png)
### MongoDB Atlas cluster
![MongoDB cluster](api_testing_screenshots/mongodbcluster.png)
### Project structure
![Project structure]()


