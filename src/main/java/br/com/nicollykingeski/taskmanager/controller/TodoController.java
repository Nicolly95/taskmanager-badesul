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
    private TodoService todoService;
    
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping    
    public Todo create(@RequestBody @Valid Todo todo) {
        return todoService.create(todo);
    }

    @GetMapping
    public List<Todo> list() {
        return todoService.list();
    }

    @PutMapping
    public List<Todo> update(@RequestBody @Valid Todo todo) {
        return todoService.update(todo);
    }

    @DeleteMapping("{id}")
    public List<Todo> delete(@PathVariable("id") Long id) {
        return todoService.delete(id);
    }

}
