package com.example.springbootserver.Todo.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootserver.Todo.Repository.TodoRepository;
import com.example.springbootserver.Todo.dto.TodoReq;
import com.example.springbootserver.Todo.model.Todo;

import lombok.extern.slf4j.Slf4j;


@Transactional(readOnly = true)
@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    @Transactional
    public List<Todo> save(final TodoReq.TodoSave todoSave){
        final Todo todo = Todo.builder()
            .userId(todoSave.getUserId())
            .title(todoSave.getTitle())
            .done(todoSave.isDone())
            .build();

        todoRepository.save(todo);
        log.info("Todo save 완료, Id : " + todo.getId());
        return todoRepository.findByUserId(todo.getUserId())
            // 익셉션 수정 바람
            .orElseThrow(() -> new RuntimeException("Todo항목이 없습니다."));
    }
}
