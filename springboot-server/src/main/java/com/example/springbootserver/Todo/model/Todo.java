package com.example.springbootserver.todo.model;

import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "todos")
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Long 으로 만들경우 
    private Long id;

    @Column(name = "user_id")    
    private Long userId;

    private String title;
    
    @Column(columnDefinition = "TINYINT(1)")
    private boolean done;
}
