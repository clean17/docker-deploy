package com.example.springbootserver.mock;

import com.example.springbootserver.config.WebSecurityConfig;
import com.example.springbootserver.core.MyWithMockUser;
import com.example.springbootserver.core.advice.MyValidAdvice;
import com.example.springbootserver.core.auth.jwt.MyJwtProvider;
import com.example.springbootserver.core.exception.Exception400;
import com.example.springbootserver.core.exception.Exception403;
import com.example.springbootserver.user.controller.UserController;
import com.example.springbootserver.user.dto.UserReq;
import com.example.springbootserver.user.model.User;
import com.example.springbootserver.user.service.UserService;
import com.example.springbootserver.user.service.UserService.LoginJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
@EnableAspectJAutoProxy
@MockBean(JpaMetamodelMappingContext.class) // 실제 DB 연결없이 테스트
@ActiveProfiles("test")
@Import({WebSecurityConfig.class, MyValidAdvice.class, MyJwtProvider.class})
public class UserMockTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    List<User> users;

    @BeforeEach
    public void setup() {
        users = List.of(
                User.builder().id(1L).email("son@gmail.com").role("USER").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).username("son").build(),
                User.builder().id(2L).email("jun@gmail.com").role("USER").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).username("jun").build());
    }

    @Test
    public void join_MockTest() throws Exception {
        // given
        UserReq.UserSave userSave = UserReq.UserSave.builder()
                .email("kane@gmail.com").password("1234").username("kane")
                .build();
        User user = User.builder()
                .id(1L).email("kane@gmail.com").role("USER").password("1234").createdAt(LocalDateTime.now()).username("kane")
                .build();
        given(userService.join(userSave)).willReturn(user);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .content(om.writeValueAsString(userSave))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // 테스트에서는 영속화가 되지 않아서 null 리턴 - @EqualsAndHashCode 붙여줌
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("kane@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("kane"))
        ;
    }

    @Test
    public void joinFail_MockTest() throws Exception {
        // given
        UserReq.UserSave userSave = UserReq.UserSave.builder()
                .email("son@gmail.com").password("1234").username("son")
                .build();
        given(userService.join(userSave)).willThrow(new Exception400(null, "이미 존재"));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/join")
                                .content(om.writeValueAsString(userSave))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("이미 존재"))
        ;
    }

    @Test
    public void login_MockTest() throws Exception {
        // given
        UserReq.UserLogin userLogin = UserReq.UserLogin.builder()
                .email("son@gmail.com").password("1234")
                .build();
        LoginJWT loginJWT = new LoginJWT();
        loginJWT.setJwt("토큰");
        loginJWT.setUser(users.get(0));
        given(userService.login(userLogin)).willReturn(loginJWT);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/login")
                                .content(om.writeValueAsString(userLogin))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("son@gmail.com"))
                .andExpect(MockMvcResultMatchers.header().exists("Authorization"))
        ;
    }

    @Test
    public void loginFail_MockTest() throws Exception {
        // given
        UserReq.UserLogin userLogin = UserReq.UserLogin.builder()
                .email("son@gmail.com").password("123887784")
                .build();
        given(userService.login(userLogin)).willThrow(new Exception400(null, "회원정보 못찾음"));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/login")
                                .content(om.writeValueAsString(userLogin))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("회원정보 못찾음"))
        ;
    }

    @Test
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void update_MockTest() throws Exception {
        // given
        UserReq.UserUpdate userUpdate = UserReq.UserUpdate.builder()
                .id(1L).password("4567").username("sonson").email("son@gmail.com")
                .build();
        User user = User.builder()
                .id(1L).email("son@gmail.com").role("USER").password("4567").createdAt(LocalDateTime.now()).username("sonson")
                .build();
        given(userService.update(userUpdate)).willReturn(user);

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/users")
                                .content(om.writeValueAsString(userUpdate))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("son@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("sonson"))
        ;
    }

    @Test
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void updateFail_MockTest() throws Exception {
        // given
        UserReq.UserUpdate userUpdate = UserReq.UserUpdate.builder()
                .id(3L).password("4567").username("sonson").email("son@gmail.com")
                .build();
        given(userService.update(userUpdate)).willThrow(new Exception400(null, "조회 결과 없음"));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/users")
                                .content(om.writeValueAsString(userUpdate))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("조회 결과 없음"))
        ;
    }

    @Test
    @MyWithMockUser(id = 1L, username = "son", role = "USER")
    public void updateUnAuthorized_MockTest() throws Exception {
        // given
        UserReq.UserUpdate userUpdate = UserReq.UserUpdate.builder()
                .id(2L).password("4567").username("sonson").email("son@gmail.com")
                .build();
        given(userService.update(userUpdate)).willThrow(new Exception403("인증 필요"));

        // when
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/users")
                                .content(om.writeValueAsString(userUpdate))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("권한이 필요합니다."))
        ;
    }
}
