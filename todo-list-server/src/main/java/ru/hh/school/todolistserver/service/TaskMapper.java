package ru.hh.school.todolistserver.service;

import org.springframework.stereotype.Service;
import ru.hh.school.todolistserver.dto.TaskDto;
import ru.hh.school.todolistserver.entity.TaskEntity;

@Service
public class TaskMapper {
    public TaskDto map(TaskEntity taskEntity){
        return new TaskDto(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getCompletionStatus());
    }
}
