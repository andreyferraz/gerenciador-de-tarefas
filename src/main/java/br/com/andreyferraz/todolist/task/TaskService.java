package br.com.andreyferraz.todolist.task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.andreyferraz.todolist.utils.UpdateUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<?> createTaskService(TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setUserId((UUID) idUser);

        var currentDate = LocalDateTime.now();
        
        if(currentDate.isAfter(taskModel.getStartDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date is invalid");
        }
        if(currentDate.isAfter(taskModel.getEndDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date is invalid");
        }
        if(taskModel.getStartDate().isAfter(taskModel.getEndDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date is invalid");
        }

        String titleTask = taskModel.getTitle();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> error = new HashMap<>();
        error.put("title", "Title is required");
        error.put("code", String.valueOf(status.value()));

        if(titleTask.isBlank()){
            return ResponseEntity.status(status).body(error);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));  
    }

    public List<TaskModel> listTaskService(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByUserId((UUID) idUser);
        return tasks;
    }

    public ResponseEntity<?> updateTaskService(TaskModel taskModel, HttpServletRequest request, UUID id){
        var idUser = request.getAttribute("idUser");
        var task = this.taskRepository.findById(id).orElse(null);
        
        if(task == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        if(!task.getUserId().equals(idUser)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UpdateUtils.copyNonNullProperties(taskModel, task);
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(task));
    }
}
