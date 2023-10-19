package br.com.todolist.TodoList.task;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * id
 * usuario (id_usuario)
 * descrição
 * titulo
 * data inicio
 * data terminio
 * prioridade
 */

 @Data
 @Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private  UUID id;
    private String description;
    
/*limitação de caracteres por campo */
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    /* atrelando o id ao usuario na tarefa*/
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /*tratamento de erros */
    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("O CAMPO TITULO DEVE CONTER APENSAS 50 CARACTERES");
        }
        this.title = title;
    }
}
