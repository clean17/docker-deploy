package com.example.springbootserver.Todo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootserver.Todo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

    Optional<List<Todo>> findByUserId(Long userId);
}
