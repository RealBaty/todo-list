package ru.hh.school.todolistserver.service;

import org.springframework.stereotype.Service;
import ru.hh.school.todolistserver.dto.TaskDto;
import ru.hh.school.todolistserver.entity.Task;

@Service
public class TaskMapper {
    public static TaskDto map(Task task){
        return new TaskDto(task.getId(), task.getTitle(), task.getCompletionStatus());
    }
}
