package br.com.todolist.TodoList.Model;

import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)/*atributo responsavel por passar um valor unico para ser permitido valores iguais */
    private String username;
    private String name;
    private String passoword;

    @CreationTimestamp
    private LocalDateTime createdAt;



    

    


    

}