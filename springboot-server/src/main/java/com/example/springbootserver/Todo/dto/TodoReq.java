package com.example.springbootserver.Todo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoReq {
    
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TodoSave {
        private String id;
        private String title;
        private boolean done;

        // public TodoSave(String id, String title, boolean done) {
        //     this.id = id;
        //     this.title = title;
        //     this.done = done;
        // }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TodoUpdate {
        private String id;
        private String title;
        private boolean done;

        // public TodoUpdate(String id, String title, boolean done) {
        //     this.id = id;
        //     this.title = title;
        //     this.done = done;
        // }
    }
}
