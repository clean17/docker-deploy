package com.example.springbootserver.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="todo")
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Long 으로 만들경우 
    // 아래는 문자열 형태의 UUID를 사용하기 위한 커스텀 generator
    // @GeneratedValue(generator = "system-uuid")
    // @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long id;
    private Long userId;
    private String title;
    private boolean done;

}
