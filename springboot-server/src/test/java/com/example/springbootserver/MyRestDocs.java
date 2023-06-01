package com.example.springbootserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
public class MyRestDocs {
    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    @BeforeEach
    private void setup(WebApplicationContext webApplicationContext,
                       RestDocumentationContextProvider restDocumentation) {
        this.document = MockMvcRestDocumentation.document("{class-name}/{method-name}",
                preprocessRequest(prettyPrint()
//                        , removeHeaders("Authorization") // 민감한 정보일 경우 문서화 x
//                        , removeHeaders("Set-Cookie")
                ),
                preprocessResponse(prettyPrint())
                );

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                // .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysDo(document) // 디폴트 문서화
                .build();
    }
    //  document 를 오버로딩하지 않고 편하게 사용할 메소드를 만들 수 없을까 ?
    protected FieldDescriptor[] todosResponseFileds() {
        return new FieldDescriptor[]{
                fieldWithPath("status").description("응답 상태"),
                fieldWithPath("msg").description("응답 메시지"),
                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("아이디"),
                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("할 일"),
                fieldWithPath("data[].done").type(JsonFieldType.BOOLEAN).description("완료 여부")
        };
    }
}
