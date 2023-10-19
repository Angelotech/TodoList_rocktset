package br.com.todolist.TodoList.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.todolist.TodoList.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository uIUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // validação de rotas (tarefa)
        var servletpath = request.getServletPath();
            
        if (servletpath.startsWith("/tasks/")){
            //pegar autentificação(usuario e senha)
            var authorization = request.getHeader("Authorization");

            //separação do basic do codigo
            var authEncode = authorization.substring("Basic".length()).trim();
            
            byte[] authDecode = Base64.getDecoder().decode(authEncode);
        
            //transformação do decode para uma string
            var authString = new String(authDecode);                
            System.out.println("Authorization");

            //["usuario", "senha"]
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println(username);
            System.out.println(password);




            //validar usuario
            var user = this.uIUserRepository.findByUsername(username);
            if(user == null){
                response.sendError(401);
            } else {
                //validar senha
                //verificação e tranformação para um array
                var PassowordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassoword());
                if(PassowordVerify.verified){
                    //setando o iduser para ser setado nos controles
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);

                } else {
                    response.sendError(401);
                }        
                
            } 
                
        } else {
            filterChain.doFilter(request, response); 
        } 

    }
  
} 

