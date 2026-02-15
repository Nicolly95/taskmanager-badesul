package br.com.nicollykingeski.taskmanager.controller;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nicollykingeski.taskmanager.entity.Todo;
import br.com.nicollykingeski.taskmanager.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/todos")
//TERÁ TODAS AS OPERAÇÕES CRIADAS NO SERVICE!
public class TodoController {
    private final TodoService todoService;
    
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //Cria nova tarefa e retornar lista atualizada
    @PostMapping    
    public List<Todo> create(@RequestBody @Valid Todo todo) {
        todoService.create(todo);
        return todoService.list();
    }

    //Lista todas as tarefas atuais
    @GetMapping
    public List<Todo> list() {
        return todoService.list();
    }

    //Atualiza tarefa existente e retorna lista atualizada
    @PutMapping
    public List<Todo> update(@RequestBody @Valid Todo todo) {
        return todoService.update(todo);
    }

    //Deleta uma tarefa a partir de um ID e retorna a lista atualizada
    @DeleteMapping("/{id}")
    public List<Todo> delete(@PathVariable("id") Long id) {
        return todoService.delete(id);
    }

}
