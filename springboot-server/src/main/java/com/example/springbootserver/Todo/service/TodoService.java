package com.example.springbootserver.Todo.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootserver.Todo.Repository.TodoRepository;
import com.example.springbootserver.Todo.model.Todo;


@Transactional(readOnly = true)
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }
}
