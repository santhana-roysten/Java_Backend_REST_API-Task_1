package com.example.task_manager;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    // Find tasks by name containing string (case-insensitive)
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Task> findByNameContaining(String name);
}