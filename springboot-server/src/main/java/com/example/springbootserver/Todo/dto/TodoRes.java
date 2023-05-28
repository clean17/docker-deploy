package com.example.springbootserver.Todo.dto;

import com.example.springbootserver.Todo.model.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoRes {
    
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TodoDto {
        private Long id;
        private String title;
        private boolean done;

        public TodoDto(final Todo todo) {
            this.id = todo.getId();
            this.title = todo.getTitle();
            this.done = todo.isDone();
        }
    }
}
