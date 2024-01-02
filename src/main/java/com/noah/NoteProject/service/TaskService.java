package com.noah.NoteProject.service;

import com.noah.NoteProject.model.Task;
import com.noah.NoteProject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task newTask(String title, String description){
        Task newTask = new Task(title, description);
        return taskRepository.save(newTask);
    }

    /**
     * 以Id搜尋Task
     * @param id
     * @return
     */
    public Task getTask(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    /**
     * 搜尋全部未完成Task
     * @return
     */
    public List<Task> getTasks(){
        return taskRepository.findByCompleted(false);
    }

    /**
     * 搜尋全部已完成Task
     * @return
     */
    public List<Task> getCompletedTasks(){
        return taskRepository.findAll().stream().filter(s -> s.isCompleted()).toList();
    }

    /**
     * 刪除Task
     * @param id
     * @return
     */
    public boolean deleteTask(Long id){
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteAllTasks(List<Task> tasks){
        if(!tasks.isEmpty()){
            for(Task t : tasks){
                deleteTask(t.getId());
            }
            return true;
        }
        return false;
    }

    /**
     * 修改Task任務
     * @param id
     * @param description
     * @return
     */
    public boolean updateDescription(Long id, String description){
        var updateTask = taskRepository.findById(id);
        if(updateTask.isPresent()){
            Task task = updateTask.get();
            task.setDescription(description);
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    /**
     * 更改完成狀態
     * @param id
     */
    public boolean completed(Long id){
        var updateTask = taskRepository.findById(id);
        if(updateTask.isPresent()){
            Task task = updateTask.get();
            task.setCompleted();
            taskRepository.save(task);
            return true;
        }
        return false;
    }

}
