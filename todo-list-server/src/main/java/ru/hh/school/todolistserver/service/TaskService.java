package ru.hh.school.todolistserver.service;

import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.todolistserver.dao.TaskDao;
import ru.hh.school.todolistserver.dto.TaskDto;
import ru.hh.school.todolistserver.entity.Task;
import ru.hh.school.todolistserver.exception.IllegalTaskException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskDao taskDao;

    @Inject
    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> find(Long id, String title, Boolean completionStatus){
        return taskDao.find(id, title, completionStatus)
                .stream()
                .map(TaskMapper::map)
                .toList();
    }

    @Transactional
    public TaskDto save(String title, Boolean completionStatus){
        return TaskMapper.map(taskDao.save(title, completionStatus));
    }

    private Task loadTask(Long id) {
        return taskDao.getTask(id)
                .orElseThrow(() -> new IllegalTaskException("Task not found by id"));
    }

    @Transactional
    public void update(Long id, String title, Boolean completionStatus){
        Task task = loadTask(id);
        Optional.ofNullable(title)
                .ifPresent(task::setTitle);
        Optional.ofNullable(completionStatus)
                .ifPresent(task::setCompletionStatus);
    }

    @Transactional
    public void remove(Long id){
        taskDao.remove(id);
    }

    @Transactional
    public void removeAll(){
        taskDao.removeAll();
    }
}
