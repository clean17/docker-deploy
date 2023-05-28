package com.example.springbootserver.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springbootserver.MyRestDocs;
import com.example.springbootserver.todo.controller.TodoController;


// @WebMvcTest(TodoController.class)
@SpringBootTest
public class TodoControllerTest extends MyRestDocs{
    // @Test
    // public void save_테스트() throws Exception {
    //     // given
    //     String content = new ObjectMapper().writeValueAsString(
    //             User.builder().username("ssar").password("1234").email("ssar@nate.com").build());

    //     // when
    //     ResultActions resultActions = mockMvc.perform(
    //             MockMvcRequestBuilders
    //                     .post("/users")
    //                     .content(content)
    //                     .contentType(MediaType.APPLICATION_JSON));
    //     // then
    //     resultActions
    //             .andExpect(MockMvcResultMatchers.status().isCreated())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ssar"))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ssar@nate.com"))
    //             .andDo(MockMvcResultHandlers.print())
    //             .andDo(document);
    // }

    // @Test
    // public void find_한건_테스트() throws Exception {
    //     // given
    //     Integer id = 1;

    //     // when
    //     ResultActions resultActions = mockMvc.perform(
    //             MockMvcRequestBuilders
    //                     .get("/users/" + id));
    //     // then
    //     resultActions
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ssar"))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ssar@nate.com"))
    //             .andDo(MockMvcResultHandlers.print())
    //             .andDo(document);
    // }

    @Test
    public void find_전체_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/todos"));
        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].title").value("아침먹고 운동하기"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].done").value(false))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }
}
