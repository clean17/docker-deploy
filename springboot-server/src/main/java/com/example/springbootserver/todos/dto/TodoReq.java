package com.example.springbootserver.todos.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springbootserver.todos.model.Todo;

import lombok.*;


public class TodoReq {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class TodoSave {

        // @NotNull(message = "로그인이 필요합니다.")
        private Long userId;

        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;

        // boolean 값을 주지 않으면 디폴트 false
        private boolean done;

        public static Todo toEntity(final TodoReq.TodoSave todoSave) {
            return Todo.builder()
                    .userId(todoSave.getUserId())
                    .title(todoSave.getTitle())
                    .done(todoSave.isDone())
                    .build();
        }
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class TodoUpdate {

        @NotNull(message = "id값이 필요합니다.") // Long 타입도 NotBlack 불가
        private Long id;

        // @NotNull(message = "로그인이 필요합니다.")
        private Long userId;

        @NotBlank(message = "타이틀을 입력하세요.") // String 만 NotBlank
        private String title;

        @NotNull(message = "done값을 입력하세요.") // boolean -> @Valid가 검사하지 못한다. Boolean 로 만들어야함
        private Boolean done;

        public static Todo toEntity(final TodoUpdate todoUpdate) {
            return Todo.builder()
                    .id(todoUpdate.getId())
                    .userId(todoUpdate.getUserId())
                    .title(todoUpdate.getTitle())
                    .done(todoUpdate.getDone())
                    .build();
        }
    }
}
