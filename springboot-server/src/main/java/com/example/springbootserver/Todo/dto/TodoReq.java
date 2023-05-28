package com.example.springbootserver.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springbootserver.todo.model.Todo;

import lombok.Getter;
import lombok.Setter;

public class TodoReq {

    @Setter
    @Getter
    public static class TodoSave {
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
        // boolean 은 NotBlank 불가 NotBlank 는 String 받을때
        @NotNull(message = "done값을 입력하세요.")
        private boolean done;

        public static Todo toEntity(final TodoSave todoSave) {
            final Todo todo = Todo.builder()
                    .userId(todoSave.getUserId())
                    .title(todoSave.getTitle())
                    .done(todoSave.isDone())
                    .build();
            return todo;
        }
    }

    @Setter
    @Getter
    public static class TodoUpdate {
        private Long id;
        private Long userId;
        @NotBlank(message = "타이틀을 입력하세요.")
        private String title;
        @NotNull(message = "done값을 입력하세요.")
        private boolean done;

        public static Todo toEntity(final TodoUpdate todoSave) {
            final Todo todo = Todo.builder()
                    .userId(todoSave.getUserId())
                    .title(todoSave.getTitle())
                    .done(todoSave.isDone())
                    .build();
            return todo;
        }
    }
}
