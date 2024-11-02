package com.example.project.entity;

import com.example.project.dto.TodoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "todo_table")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=200)
    private String todoTask;

    @Column(nullable = false)
    private Boolean todoCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static TodoEntity toTodoEntity(TodoDTO todoDTO, MemberEntity memberEntity) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(todoDTO.getId());
        todoEntity.setTodoTask(todoDTO.getTodoTask());
        todoEntity.setTodoCompleted(todoDTO.isTodoCompleted());
        todoEntity.setMemberEntity(memberEntity);
        return todoEntity;
    }
}
