package br.com.todolist.TodoList.Controller;
import br.com.todolist.TodoList.IUserRepository;
import br.com.todolist.TodoList.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * camadas de acesso para o usuario solicitar
 * Metodos do HTTP
 * get - buscar informação
 * post - adicionar dados
 * delete - remover dados
 * put - altera um dado/info
 * path - alterar somente uma parte da operação
 * */

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**criação de rotas*/

@RestController
@RequestMapping("/Users") /**rota de acesso*/

public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
    var user = this.userRepository.findByUsername(userModel.getUsername());
    /*linha responsavel para saber se o usuario ja foi criado */
    if(user != null){
        System.out.println("usuario ja existente");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existente");
    }
    /*linha responsavel pela criptografia */
       var passwordHashred = BCrypt.withDefaults().
       hashToString(12, userModel.getPassoword().toCharArray());
       userModel.setPassoword(passwordHashred);

       var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }



}
