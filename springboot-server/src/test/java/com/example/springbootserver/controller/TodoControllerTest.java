package com.example.springbootserver.controller;

import com.example.springbootserver.core.MyRestDocs;
import com.example.springbootserver.core.MyWithMockUser;
import com.example.springbootserver.todo.dto.TodoReq;
import com.example.springbootserver.todo.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@ActiveProfiles("test")
public class TodoControllerTest extends MyRestDocs {

    List<Todo> todos;

    @Autowired
    private ObjectMapper om;

//    @BeforeEach
//    public void setup() {
//        todos = List.of(
//                Todo.builder().id(1L).userId(1L).title("아침먹고 운동하기").done(false).build(),
//                Todo.builder().id(2L).userId(1L).title("점심먹고 운동하기").done(false).build());
//    }

    @Test
    @Transactional
    @MyWithMockUser(id = 1L, username = "ssar", role = "USER")
    public void findAll_테스트() throws Exception {
        this.mockMvc.perform(get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(todosResponseFileds())
                ));
    }

    @Test
    @Transactional
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void findOne_테스트() throws Exception {
        Long id = 2L;
        this.mockMvc.perform(get("/todos/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("id").description("Todo 아이디")),
                        responseFields(todoResponseFileds())
                ));
    }

    @Test
//    @Transactional
    @MyWithMockUser(id = 2, username = "ssar", role = "USER")
    public void save_테스트() throws Exception {
        TodoReq.TodoSave todoSave = TodoReq.TodoSave.builder()
                .userId(2L).title("이마트 가기").done(false)
                .build();

        this.mockMvc.perform(post("/todos")
                        .content(om.writeValueAsString(todoSave))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(todoResponseFileds())
                ));
    }


    @Test
    @Transactional
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void update_테스트() throws Exception {
        TodoReq.TodoUpdate todoUpdate = TodoReq.TodoUpdate.builder()
                .id(1L).userId(1L).title("아침먹고 공부하기").done(true)
                .build();

        this.mockMvc.perform(put("/todos")
                        .content(om.writeValueAsString(todoUpdate))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(todoResponseFileds())
                ));
    }


    @Test
    @Transactional
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void delete_테스트() throws Exception {
        Long id = 2L;
        this.mockMvc.perform(get("/todos/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}
