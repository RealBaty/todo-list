package ru.hh.school.todolistserver.dao;

import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.hh.school.todolistserver.entity.TaskEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDao {

    private final SessionFactory sessionFactory;

    @Inject
    public TaskDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<TaskEntity> find(Optional<Long> id, Optional<String> title, Optional<Boolean> completionStatus){
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> criteriaQuery = builder.createQuery(TaskEntity.class);
        Root<TaskEntity> root = criteriaQuery.from(TaskEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        id.ifPresent(field ->
                predicates.add(builder.equal(root.get("id"), field)));
        title.ifPresent(field ->
                predicates.add(builder.equal(root.get("title"), field)));
        completionStatus.ifPresent(field ->
                predicates.add(builder.equal(root.get("completionStatus"), field)));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(criteriaQuery).getResultList();
    }

    public Optional<TaskEntity> getTask(Long id){
        return Optional.ofNullable(getSession().get(TaskEntity.class, id));
    }

    public void update(TaskEntity taskEntity){
        getSession().update(taskEntity);
    }

    public TaskEntity save(String title, Boolean completionStatus){
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setCompletionStatus(completionStatus);
        getSession().persist(task);
        return task;
    }

    public void remove(Long id){
        getSession()
                .createQuery("DELETE TaskEntity t WHERE t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void removeAll(){
        getSession()
                .createQuery("DELETE TaskEntity")
                .executeUpdate();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
