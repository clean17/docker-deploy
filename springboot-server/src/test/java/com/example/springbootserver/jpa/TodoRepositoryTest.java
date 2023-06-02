package com.example.springbootserver.jpa;

import com.example.springbootserver.todo.model.Todo;
import com.example.springbootserver.todo.model.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    public void findAll_test() {
        List<Todo> todoList = todoRepository.findAll();
        Assertions.assertEquals(todoList.get(0).getId(), 1);
        Assertions.assertEquals(todoList.get(1).getTitle(), "점심먹고 운동하기");
    }

    @Test
    @Transactional
    public void findById_test(){
        Optional<Todo> todo = todoRepository.findById(1L);
        Assertions.assertEquals(todo.get().getTitle(), "아침먹고 운동하기");
    }

    @Test
    @Transactional
    public void save_test() {
        Todo todo = Todo.builder()
                .userId(1L).title("밥먹고 산책하기").done(false).build();
        Todo saveTodo = todoRepository.save(todo);
        Assertions.assertEquals(saveTodo.isDone(), false);
        Assertions.assertEquals(saveTodo.getTitle(), "밥먹고 산책하기");
    }

    @Test
    @Transactional
    public void update_test() {
        Todo todo = Todo.builder()
                .id(1L).userId(1L).title("밥먹고 산책하기").done(false).build();
        Todo updateTodo = todoRepository.save(todo);
        Assertions.assertEquals(updateTodo.getId(), 1);
        Assertions.assertEquals(updateTodo.getTitle(), "밥먹고 산책하기");
    }

    @Test
    @Transactional
    public void delete_test() {
        todoRepository.deleteById(1L);
        Assertions.assertFalse(todoRepository.findById(1L).isPresent());
    }

}
