package br.com.nicollykingeski.taskmanager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import br.com.nicollykingeski.taskmanager.entity.Todo;


//Usamos nos testes o banco H2
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskmanagerApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateTodoSuccess() {
		var todo = new Todo("todo 1", "description todo 1", false, 1);

		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(todo)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].nome").isEqualTo(todo.getNome())
			.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
			.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
			.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());	
	}

	@Test
	//Uma Todo inválida seria com um nome vazio ou descrição vazia
	void testCreateTodoFailure() {
		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(
				new Todo("", "", false, 0)
			).exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	void testListTodoSuccess() {
		var todo = new Todo("todo 1", "description todo 1", false, 1);

		//garante que há dados no BD
		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(todo)
			.exchange()
			.expectStatus().isOk();

		//agora lista os dados com .get()
		webTestClient
			.get()
			.uri("/todos")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].nome").isEqualTo(todo.getNome())
			.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao());	
	}

	@Test
	//previne erro 500 se o banco estiver limpo
	void testListTodoEmpty() {
		webTestClient
			.get()
			.uri("/todos")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(0);
	}

}
























