package com.example.project.repository;

import com.example.project.entity.MemberEntity;
import com.example.project.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByMemberEntity(MemberEntity memberEntity);
}
