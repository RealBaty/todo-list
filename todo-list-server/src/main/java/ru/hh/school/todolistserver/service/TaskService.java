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
    public List<TaskEntity> find(Optional<Long> id, Optional<String> title, Optional<Boolean> completionStatus){
        return taskDao.find(id, title, completionStatus);
    }

    @Transactional
    public TaskEntity save(String title, Boolean completionStatus){
        return taskDao.save(title, completionStatus);
    }

    @Transactional
    public Boolean update(Long id, Optional<String> title, Optional<Boolean> completionStatus){
        Optional<TaskEntity> task = taskDao.getTask(id);
        if(task.isPresent()){
            title.ifPresent(task.get()::setTitle);
            completionStatus.ifPresent(task.get()::setCompletionStatus);
            return true;
        }
        return false;
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
