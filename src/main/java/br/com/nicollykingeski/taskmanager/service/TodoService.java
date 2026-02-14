package br.com.nicollykingeski.taskmanager.service;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.nicollykingeski.taskmanager.entity.Todo;
import br.com.nicollykingeski.taskmanager.repository.TodoRepository;

@Service
public class TodoService {    
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    //cria lista do tipo Todo para CRIAR nova task
    public List<Todo> create(Todo todo) {
        todoRepository.save(todo);
        return list();
    }

    //cria lista do tipo Todo para CONSULTAR tasks existentes
    //retorna a lista de forma ordenada primeiramente por prioridade e depois por ordem alfabetica
    public List<Todo> list() {
        Sort sort = Sort.by("prioridade").descending().and(
            Sort.by("nome").ascending());
        return todoRepository.findAll(sort);                                               
    }

    //cria lista do tipo Todo para ATUALIZAR uma task
    public List<Todo> update(Todo todo) {
        if(todo.getId() != null && todoRepository.existsById(todo.getId())) {
            todoRepository.save(todo);
            return list();
        }

        throw new RuntimeException("Tarefa não encontrada ou ID inválido.");        
    }

    //cria lista do tipo Todo para DELETAR uma task
    //deleta somente o item do identificador unico desejado
    public List<Todo> delete(Long id) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return list();
        }        

        throw new RuntimeException("Tarefa não encontrada");
    }
    
}
















