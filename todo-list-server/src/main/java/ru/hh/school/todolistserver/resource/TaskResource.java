package ru.hh.school.todolistserver.resource;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import ru.hh.school.todolistserver.dto.TaskDto;
import ru.hh.school.todolistserver.service.TaskMapper;
import ru.hh.school.todolistserver.service.TaskService;
import ru.hh.school.todolistserver.service.TaskValidator;

import java.util.Optional;

@Path(value = "/task")
@Controller
@Resource
public class TaskResource {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final TaskValidator taskValidator;

    @Autowired
    public TaskResource(TaskService taskService, TaskMapper taskMapper, TaskValidator taskValidator){
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskValidator = taskValidator;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@QueryParam(value = "id") Long id,
                         @QueryParam(value = "title") String title,
                         @QueryParam(value = "completionStatus") Boolean completionStatus){
        return Response
                .ok(taskService.find(Optional.ofNullable(id), Optional.ofNullable(title), Optional.ofNullable(completionStatus)))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(TaskDto taskDto){
        taskValidator.validateNewTask(taskDto);
        return Response
                .ok(taskMapper.map(
                        taskService.save(taskDto.getTitle(), taskDto.getCompleted())))
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(TaskDto taskDto){
        taskValidator.validateUpdateTask(taskDto);
        taskService.update(
                taskDto.getId(),
                Optional.ofNullable(taskDto.getTitle()),
                Optional.ofNullable(taskDto.getCompleted()));
        return Response.ok().build();
    }

    @DELETE
    public Response remove(@QueryParam(value = "id") Long id){
        if(id == null){
            taskService.removeAll();
        } else {
            taskService.remove(id);
        }
        return Response.ok().build();
    }
}
