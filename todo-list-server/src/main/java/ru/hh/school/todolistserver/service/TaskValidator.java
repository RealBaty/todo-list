package ru.hh.school.todolistserver.service;

import org.springframework.stereotype.Service;
import ru.hh.school.todolistserver.dto.TaskDto;

@Service
public class TaskValidator {
    public void validateNewTask(TaskDto resumeDto) {
        String title = resumeDto.getTitle();
        Boolean completed = resumeDto.getCompleted();

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is not present");
        }
        if (completed == null) {
            throw new IllegalArgumentException("Completed is not present");
        }
    }

    public void validateUpdateTask(TaskDto resumeDto) {
        Long id = resumeDto.getId();

        if (id == null) {
            throw new IllegalArgumentException("Id is not present");
        }
    }
}
