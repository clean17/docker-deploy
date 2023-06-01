package com.example.springbootserver.jpa;

import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.model.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void findAll_test(){
        List<Todo> todoList = todoRepository.findAll();
        Assertions.assertEquals(todoList.get(0).getId(), 1);
        Assertions.assertEquals(todoList.get(1).getTitle(), "점심먹고 운동하기");
    }

}
