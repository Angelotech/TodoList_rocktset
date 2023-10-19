package br.com.todolist.TodoList;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.todolist.TodoList.Model.UserModel;

public interface IUserRepository extends JpaRepository <UserModel, UUID>{
    UserModel findByUsername(String username);
    
}
    

