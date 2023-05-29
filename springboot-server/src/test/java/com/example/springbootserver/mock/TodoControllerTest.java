package com.example.springbootserver.mock;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springbootserver.MyRestDocs;
import com.example.springbootserver.config.MySecurityConfig;
import com.example.springbootserver.core.advice.MyValidAdvice;
import com.example.springbootserver.todo.controller.TodoController;
import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.service.TodoService;

// @AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
// @SpringBootTest

@WebMvcTest(TodoController.class)
@EnableAspectJAutoProxy
@Import({MySecurityConfig.class, MyValidAdvice.class})
public class TodoControllerTest extends MyRestDocs {

    @MockBean
    private TodoService todoService;

    List<Todo> todos;

    @BeforeEach
    public void setup() {
        todos = List.of(
                Todo.builder().id(1L).userId(1L).title("아침먹고 운동하기").done(false).build(),
                Todo.builder().id(2L).userId(1L).title("점심먹고 운동하기").done(false).build());
    }

    @Test
    public void find_전체_테스트() throws Exception {
        // given
        given(todoService.findAll()).willReturn(todos);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/todos"));

        // then
         resultActions
         .andExpect(MockMvcResultMatchers.status().isOk())
         // jsonPath 는 응답된 json을 검증할때 사용 ( $.name / $[0].name )
         .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1L))
         .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("아침먹고 운동하기"))
         .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].done").value(false))
         .andDo(MockMvcResultHandlers.print())
         .andDo(document);
    }
}
