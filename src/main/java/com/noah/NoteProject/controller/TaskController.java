package com.noah.NoteProject.controller;

import com.noah.NoteProject.model.Task;
import com.noah.NoteProject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    @GetMapping("/search/todo")
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }
    @GetMapping("/search/done")
    public List<Task> getAllCompletedTasks(){
        return taskService.getCompletedTasks();
    }
    @GetMapping("/search/{id}")
    public Task getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }
    @PostMapping("/create")
    public Task createTask(@RequestParam String title, String description){
        return taskService.newTask(title, description);
    }
    @DeleteMapping("/trash/{id}")
    public boolean delete(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
    @PatchMapping("/complete/{id}")
    public boolean completeChange(@PathVariable Long id){
        return taskService.completed(id);
    }
}
