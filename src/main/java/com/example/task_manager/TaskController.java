package com.example.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // GET all tasks or single task by ID
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Task> task = taskRepository.findById(id);
            if (task.isPresent()) {
                return ResponseEntity.ok(task.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Task not found with ID: " + id);
            }
        }
        return ResponseEntity.ok(taskRepository.findAll());
    }

    // GET tasks by name search
    @GetMapping("/search")
    public ResponseEntity<?> getTasksByName(@RequestParam String name) {
        List<Task> tasks = taskRepository.findByNameContaining(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No tasks found containing: " + name);
        }
        return ResponseEntity.ok(tasks);
    }

    // CREATE or UPDATE task
    @PutMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        // Basic command validation
        if (!isCommandSafe(task.getCommand())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Command contains unsafe characters");
        }

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    // DELETE task
    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with ID: " + id);
        }
    }

    // EXECUTE command (simplified version - runs locally)
    @PutMapping("/execute")
    public ResponseEntity<?> executeTask(@RequestParam String id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with ID: " + id);
        }

        Task task = taskOpt.get();
        TaskExecution execution = executeCommand(task.getCommand());

        // Add execution to task
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(List.of(execution));
        } else {
            task.getTaskExecutions().add(execution);
        }

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    // Basic command safety check
    private boolean isCommandSafe(String command) {
        if (command == null) return false;

        // Block potentially dangerous commands
        String[] dangerousPatterns = {"rm ", "del ", "format ", "shutdown", "> /", "|", "&", ";"};
        for (String pattern : dangerousPatterns) {
            if (command.toLowerCase().contains(pattern)) {
                return false;
            }
        }
        return true;
    }

    // Simple command execution (runs locally)
    private TaskExecution executeCommand(String command) {
        try {
            Date startTime = new Date();

            // For safety, we'll simulate execution instead of running actual commands
            String output = "Simulated execution of: " + command + "\nOutput: Hello World!";

            // Simulate execution time
            Thread.sleep(1000);

            Date endTime = new Date();

            return new TaskExecution(startTime, endTime, output);

        } catch (Exception e) {
            return new TaskExecution(new Date(), new Date(), "Error: " + e.getMessage());
        }
    }
}
