package com.example.springbootserver.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.model.TodoRepository;
import com.example.springbootserver.core.exception.Exception500;
import com.example.springbootserver.todo.dto.TodoReq;
import com.example.springbootserver.todo.dto.TodoReq.TodoSave;

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
    public List<Todo> findAll() {
        try {
            List<Todo> result = todoRepository.findAll();
            if (ObjectUtils.isEmpty(result)) {
                result = new ArrayList<>();
            }
            return result;
        } catch (Exception500 e) {
            throw new Exception500("조회에 실패했습니다.");
        }
    }

    @Transactional
    public List<Todo> save(final TodoReq.TodoSave todoSave) {
        try {
            Todo todo = TodoSave.toEntity(todoSave);
            todoRepository.save(todo);
            log.info("Todo save 완료, Id : " + todo.getId());
            return todoRepository.findByUserId(todo.getUserId())
                    .orElseThrow(() -> new Exception500("조회 데이터가 없습니다."));
        } catch (Exception500 e) {
            throw new Exception500("조회에 실패했습니다.");
        }

    }
}
