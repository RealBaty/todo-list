package ru.hh.school.todolistserver.service;

import jakarta.inject.Inject;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.todolistserver.dao.TaskDao;
import ru.hh.school.todolistserver.entity.TaskEntity;

import java.util.ArrayList;
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
    public List<TaskEntity> find(Long id, String title, Boolean completionStatus){
        return taskDao.find(id, title, completionStatus);
    }

    @Transactional
    public TaskEntity save(String title, Boolean completionStatus){
        return taskDao.save(title, completionStatus);
    }

    @Transactional
    public void update(Long id, String title, Boolean completionStatus){
        Optional<TaskEntity> task = taskDao.getTask(id);
        if(task.isPresent()){
            Optional.ofNullable(title).ifPresent(task.get()::setTitle);
            Optional.ofNullable(completionStatus).ifPresent(task.get()::setCompletionStatus);
        } else {
            throw new IllegalArgumentException("Task not found by id");
        }
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
