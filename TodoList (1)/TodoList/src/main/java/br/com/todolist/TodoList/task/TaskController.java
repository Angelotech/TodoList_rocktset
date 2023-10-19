package br.com.todolist.TodoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.util.Util;
import jakarta.servlet.http.HttpServletRequest;
/**
 * TaskController
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private lTaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser ((UUID) idUser);

        //validação da data o LocalDatetime.now pegar a data do sistema
         var currenDate = LocalDateTime.now();
        if(currenDate.isAfter(taskModel.getStartAt()) || currenDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("a data de inicio  dever ser maior do que a data atual");

        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("a data de inicio deve ser menor do que a data de termino");
        }
        

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    //listagem das tarefas 
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");

        var tasks = this.taskRepository.findByIdUser((UUID) idUser);

        return tasks;
        
    }

    //http://localhost:8080/tasks/4454545544*/
 
    @PutMapping("/{id}")
    public ResponseEntity update (@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){

        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("tarefa não encontrada");
        }

        /*validação caso o id de quem cirou a tarefa for diferente o usuario que criou a tarefa possa realizar alterações */
        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("usuario não tem permissão para altera a tarefa");
        
        }

        Util.copyNonNullProperties(taskModel, task);
        var taskUpdate = this.taskRepository.save(task);
        return ResponseEntity.ok().body(this.taskRepository.save(taskUpdate));
    }



    
}