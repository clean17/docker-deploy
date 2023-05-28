package com.example.springbootserver.todo.dto;

import com.example.springbootserver.todo.model.Todo;

import lombok.Getter;

public class TodoRes {
    
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
