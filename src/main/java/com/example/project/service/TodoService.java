package com.example.project.service;

import com.example.project.dto.TodoDTO;
import com.example.project.entity.MemberEntity;
import com.example.project.entity.TodoEntity;
import com.example.project.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 투두리스트 저장
    public void save(TodoDTO todoDTO, MemberEntity memberEntity) {
        TodoEntity todoEntity = TodoEntity.toTodoEntity(todoDTO, memberEntity);
        todoRepository.save(todoEntity);
    }

    // 사용자별 투두리스트 조회
    public List<TodoDTO> findAllByMember(MemberEntity memberEntity) {
        List<TodoEntity> todoEntities = todoRepository.findByMemberEntity(memberEntity);
        return todoEntities.stream()
                .map(TodoDTO::toTodoDTO)
                .collect(Collectors.toList());
    }

    // 투두리스트 수정
    public void update(TodoDTO todoDTO, MemberEntity memberEntity) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(todoDTO.getId());

        if (optionalTodoEntity.isPresent()) {
            TodoEntity todoEntity = optionalTodoEntity.get();
            todoEntity.setTodoTask(todoDTO.getTodoTask());
            todoEntity.setTodoCompleted(todoDTO.isTodoCompleted());
            todoEntity.setMemberEntity(memberEntity);

            todoRepository.save(todoEntity);
        }
    }

    // 투두리스트 완료
    public void complete(TodoDTO todoDTO) {
        TodoEntity todoEntity = todoRepository.findById(todoDTO.getId()).orElseThrow();
        todoEntity.setTodoCompleted(todoDTO.isTodoCompleted());
        todoRepository.save(todoEntity);
    }

    // 투두리스트 삭제
    public void delete(TodoDTO todoDTO) {
        todoRepository.deleteById(todoDTO.getId());
    }
}