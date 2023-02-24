package ru.hh.school.todolistserver.dao;

import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.hh.school.todolistserver.entity.TaskEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDao {

    public SessionFactory sessionFactory;

    @Inject
    public TaskDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<TaskEntity> find(Optional<Long> id, Optional<String> title, Optional<Boolean> completionStatus){
        List<TaskEntity> result = new ArrayList<>();
        return result;
    }

    public List<TaskEntity> findAll(){
        List<TaskEntity> result = new ArrayList<>();
        return result;
    }

    public void saveOrUpdate(TaskEntity task){

    }
}
