package br.com.nicollykingeski.taskmanager.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.nicollykingeski.taskmanager.entity.Todo;

//Em generics < > informa a entidade "Todo" o tipo do ID da entidade "Long"
public interface TodoRepository extends JpaRepository<Todo, Long>{

    
}
