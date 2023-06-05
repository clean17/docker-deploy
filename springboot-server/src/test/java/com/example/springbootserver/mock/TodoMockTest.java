package com.example.springbootserver.mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import com.example.springbootserver.todo.dto.TodoReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springbootserver.config.MySecurityConfig;
import com.example.springbootserver.core.advice.MyValidAdvice;
import com.example.springbootserver.todo.controller.TodoController;
import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.service.TodoService;

// @AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
//@SpringBootTest

@WebMvcTest(TodoController.class)
@EnableAspectJAutoProxy
@MockBean(JpaMetamodelMappingContext.class) // 실제 DB 연결없이 테스트
@Import({MySecurityConfig.class, MyValidAdvice.class})
public class TodoMockTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    List<Todo> todos;

    @BeforeEach
    public void setup() {
        todos = List.of(
                Todo.builder().id(1L).userId(1L).title("아침먹고 운동하기").done(false).build(),
                Todo.builder().id(2L).userId(1L).title("점심먹고 운동하기").done(false).build());
//        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_MockTest() throws Exception {
        // given
        Long id = 1L;
        // given(todoService.findAll()).willReturn(todos);
       given(todoService.findbyUserId(id)).willReturn(todos);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/todos"))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                // jsonPath 는 응답된 json을 검증할때 사용 ( $.name / $[0].name )
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("아침먹고 운동하기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].done").value(false));
    }

    @Test
    public void findAllFail_MockTest() throws Exception {
        // given
        Long id = 2L;
        given(todoService.findbyUserId(id)).willReturn(new ArrayList<>());

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/todos"))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(new ArrayList<>()));

    }

    @Test
    public void findOne_MockTest() throws Exception {
        // given
        Long id = 1L;
        given(todoService.findbyId(id)).willReturn(todos.get(0));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/todos/{id}", id))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("아침먹고 운동하기"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.done").value(false));
    }

    @Test
    public void findOneFail_MockTest() throws Exception {
        // given
        Long id = 1L;
        given(todoService.findbyId(id)).willReturn(null);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/todos/{id}", id))
                // then
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void save_MockTest() throws Exception {
        // given
        TodoReq.TodoSave todoSave = TodoReq.TodoSave.builder()
                .userId(1L).title("이마트 가기").done(false)
                .build();
        Todo todo = Todo.builder()
                .id(3L).userId(1L).title("이마트 가기").done(false)
                .build();
        given(todoService.save(todoSave)).willReturn(todo);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/todos")
                                .content(om.writeValueAsString(todoSave))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // 테스트에서는 영속화가 되지 않아서 null 리턴 - @EqualsAndHashCode 붙여줌
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("이마트 가기"));
    }

    @Test
    public void saveFail_MockTest() throws Exception {
        // given
        TodoReq.TodoSave todoSave = TodoReq.TodoSave.builder()
                .userId(1L).done(false)
                .build();
        Todo todo = Todo.builder()
                .id(3L).userId(1L).title("이마트 가기").done(false)
                .build();
        given(todoService.save(todoSave)).willReturn(todo);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/todos")
                                .content(om.writeValueAsString(todoSave))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("타이틀을 입력하세요."));
    }

    @Test
    public void update_MockTest() throws Exception {
        // given
        TodoReq.TodoUpdate todoUpdate = TodoReq.TodoUpdate.builder()
                .id(1L).userId(1L).title("아침먹고 공부하기").done(true)
                .build();
        // any 를 넣으면 주어진 타입의 인스턴스라 판단함 - 무조건 반환됨
        given(this.todoService.update(any(TodoReq.TodoUpdate.class))).willReturn(
                new Todo(1L, 1L, "아침먹고 공부하기", true));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/todos")
                                .content(om.writeValueAsString(todoUpdate))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("아침먹고 공부하기"));
    }

    @Test
    public void delete_MockTest() throws Exception {
        // given
        Long id = 2L;

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/todos/{id}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void deleteFail_MockTest() throws Exception {
        // given
        Long id = null;

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/todos/{id}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
}
