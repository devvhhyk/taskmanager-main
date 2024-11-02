package com.example.project.dto;

import com.example.project.entity.TodoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoDTO {
    private Long id;
    private String todoTask;
    private boolean todoCompleted;
    private String memberId;
    public static TodoDTO toTodoDTO(TodoEntity todoEntity) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todoEntity.getId());
        todoDTO.setTodoTask(todoEntity.getTodoTask());
        todoDTO.setTodoCompleted(todoEntity.getTodoCompleted());
        todoDTO.setMemberId(todoEntity.getMemberEntity().getMemberId());
        return todoDTO;
    }
}
