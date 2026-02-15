package br.com.nicollykingeski.taskmanager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import br.com.nicollykingeski.taskmanager.entity.Todo;
import br.com.nicollykingeski.taskmanager.repository.TodoRepository;
import br.com.nicollykingeski.taskmanager.service.TodoService;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository; // Cria uma copia falsa do banco de dados

    @InjectMocks
    private TodoService todoService; // Injeta a copia falsa dentro do TodoService


    //Teste de criação de tarefa
    @Test
    void testCreateTodo() {
        Todo todo = new Todo("Novo", "Description", false, 1);
        when(todoRepository.save(todo)).thenReturn(todo);
        when(todoRepository.findAll(any(Sort.class))).thenReturn(List.of(todo));

        Todo result = todoService.create(todo);

        assertNotNull(result);
        verify(todoRepository).save(todo);
    }

    //Teste de listagem de tarefas
    @Test
    void testListTodo() {
        Todo todo = new Todo("Teste", "Description", false, 1);
        when(todoRepository.findAll(any(Sort.class))).thenReturn(List.of(todo));
       
        List<Todo> result = todoService.list();
       
        assertEquals(1, result.size());
        verify(todoRepository).findAll(any(Sort.class));
    }

    
    //Teste de Erro no Update
    @Test
    void testUpdateTodoThrowsExceptionWhenNotFound() {
        Todo todo = new Todo("Update", "Description", false, 1);
        todo.setId(1L);

        //Simula que o ID não existe no banco de dados
        when(todoRepository.existsById(1L)).thenReturn(false);

        //Verifica se lança um RuntimeException 
        assertThrows(RuntimeException.class, () -> todoService.update(todo));
        
        //Verifica se o 'save' NUNCA foi chamado
        verify(todoRepository, never()).save(any());
    }

    // Teste de Update com Sucesso
    @Test
    void testUpdateTodoSuccess() {
        Todo todo = new Todo("Nome Atualizado", "Desc", true, 2);
        todo.setId(1L);

        // Simula que o ID existe e que o banco salva e retorna a lista
        when(todoRepository.existsById(1L)).thenReturn(true);
        when(todoRepository.save(todo)).thenReturn(todo);
        when(todoRepository.findAll(any(Sort.class))).thenReturn(List.of(todo));

        List<Todo> result = todoService.update(todo);

        assertEquals(1, result.size());
        assertEquals("Nome Atualizado", result.get(0).getNome());
        verify(todoRepository).save(todo);
    }

    // Teste de Delete com Sucesso
    @Test
    void testDeleteTodoSuccess() {
        Long id = 1L;
        when(todoRepository.existsById(id)).thenReturn(true);
        when(todoRepository.findAll(any(Sort.class))).thenReturn(List.of());

        List<Todo> result = todoService.delete(id);

        assertTrue(result.isEmpty());
        verify(todoRepository).deleteById(id);
    }

    //Teste de Erro no Delete quando o ID não for encontrado
    @Test
    void testDeleteTodoFailure() {
        Long idInexistente = 9999L;
        when(todoRepository.existsById(idInexistente)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> todoService.delete(idInexistente));
        
        verify(todoRepository, never()).deleteById(anyLong());
    }



}







