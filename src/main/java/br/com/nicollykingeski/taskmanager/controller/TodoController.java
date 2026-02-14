package br.com.nicollykingeski.taskmanager.controller;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.nicollykingeski.taskmanager.entity.Todo;
import br.com.nicollykingeski.taskmanager.service.TodoService;

@RestController
@RequestMapping("/todos")
//TERÁ TODAS AS OPERAÇÕES CRIADAS NO SERVICE!
public class TodoController {
    private TodoService todoService;
    
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<Todo> create(Todo todo) {
        return todoService.create(todo);
    }

    public List<Todo> list() {
        return todoService.list();
    }

    public List<Todo> update(Todo todo) {
        return todoService.update(todo);
    }

    public List<Todo> delete(Long id) {
        return todoService.delete(id);
    }

}
