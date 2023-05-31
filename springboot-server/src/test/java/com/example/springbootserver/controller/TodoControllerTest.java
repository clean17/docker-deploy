package com.example.springbootserver.controller;

import com.example.springbootserver.MyRestDocs;
import com.example.springbootserver.todo.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
public class TodoControllerTest extends MyRestDocs {

    List<Todo> todos;

    @BeforeEach
    public void setup() {
        todos = List.of(
                Todo.builder().id(1L).userId(1L).title("아침먹고 운동하기").done(false).build(),
                Todo.builder().id(2L).userId(1L).title("점심먹고 운동하기").done(false).build());
    }

    @Test
    public void find_전체_테스트() throws Exception {

        this.mockMvc.perform(get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("msg").description("응답 메시지"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("할 일"),
                                fieldWithPath("data[].done").type(JsonFieldType.BOOLEAN).description("완료 여부")
                        )));

        ;
    }
}
