package br.com.andreyferraz.todolist.task;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping ("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<?> createTask(@RequestBody TaskModel taskModel, HttpServletRequest request){
        return taskService.createTaskService(taskModel, request);
    }

    @GetMapping("/list")
    public List<TaskModel> listTasks(HttpServletRequest request){
        List<TaskModel> tasks = taskService.listTaskService(request);
        return tasks;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskModel taskModel, 
                                        HttpServletRequest request, @RequestParam("id") UUID id){
        return taskService.updateTaskService(taskModel, request, id);
    }
    
}
