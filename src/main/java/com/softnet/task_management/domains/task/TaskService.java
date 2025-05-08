package com.softnet.task_management.domains.task;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryRepository;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.domains.user.UserService;
import com.softnet.task_management.enums.StatusEnum;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.web.controllers.task.TaskRequestDTO;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

  private final TaskCategoryRepository taskCategoryRepository;
  private final TaskRepository taskRepository;
  private final UserService userService;

  @Transactional
  public TaskResponseDTO createTask(TaskRequestDTO task, Long cateId) throws EntityNotFoundException {

    TaskCategoryEntity categoryEntity = taskCategoryRepository.findById(cateId)
      .orElseThrow(() -> new EntityNotFoundException("Category not found"));

    TaskEntity taskEntity = TaskEntity
      .builder()
      .taskCategory(categoryEntity)
      .title(task.title())
      .status(task.status())
      .assignee(task.assignee())
      .description(task.description())
      .endDate(task.endDate())
      .startDate(task.startDate())
      .loggedHour(task.loggedHour())
      .build();

    TaskEntity task1 = taskRepository.save(taskEntity);

    return TaskResponseDTO
      .builder()
      .id(task1.getTaskId())
      .endDate(task1.getEndDate())
      .status(task1.getStatus())
      .startDate(task.startDate())
      .build();

  }

  public TaskEntity createTask(TaskRequestDTO task) {

    TaskEntity taskEntity = TaskEntity
      .builder()
      .title(task.title())
      .taskCategory(task.taskCategory())
      .loggedHour(task.loggedHour())
      .startDate(task.startDate())
      .endDate(task.endDate())
      .description(task.description())
      .assignee(task.assignee())
      .status(task.status())
      .build();

    return taskRepository.save(taskEntity);

  }


  public TaskEntity taskById(Long id) {
    return taskRepository.findById(id)
      .orElseThrow();
  }

  @Transactional
  public Boolean assignTaskToUser(Long taskId, Long userId) throws EntityNotFoundException {

    TaskEntity task = taskRepository
      .findById(taskId)
      .orElseThrow(() -> new EntityNotFoundException("Task with the id = " + taskId + "cannot be found"));

    UserEntity user = userService.userById(userId);

    task.setAssignee(user);
    TaskEntity taskEntity = taskRepository.save(task);

    List<TaskEntity> tasks = user.getTasks();
    tasks.add(taskEntity);

    user.setTasks(tasks);
    UserEntity userEntity = userService.saveUser(user);

    return userEntity != null;

  }

  @Transactional
  public List<TaskResponseDTO> taskAssigned(String username) throws EntityNotFoundException {

    UserEntity user = userService.email(username);
    List<TaskEntity> tasks = taskRepository.findByAssigneeOrderByStartDateAsc(user);


    return tasks
      .stream()
      .map(task -> {

          TaskCategoryEntity taskCategoryEntity = task.getTaskCategory();

          TaskResponseDTO.CategoryOfTask categoryOfTask = TaskResponseDTO.CategoryOfTask
            .builder()
            .categoryId(taskCategoryEntity.getCategoryId())
            .name(taskCategoryEntity.getName())
            .description(taskCategoryEntity.getDescription())
            .build();

          UserEntity userEntity = task.getAssignee();

          TaskResponseDTO.UserTask task1 = TaskResponseDTO.UserTask
            .builder()
            .role(userEntity.getRole())
            .email(userEntity.getEmail())
            .name(userEntity.getName())
            .userId(userEntity.getUserId())
            .build();

          List<LoggedHourEntity> loggedHours = task.getLoggedHour();
          List<TaskResponseDTO.LoggedTask> loggedTasks = new ArrayList<>();


          for (LoggedHourEntity loggedHour : loggedHours) {

            TaskResponseDTO.LoggedTask loggedTask = TaskResponseDTO.LoggedTask
              .builder()
              .loggedHourId(loggedHour.getLoggedHourId())
              .dateLogged(loggedHour.getDateLogged())
              .timeSpent(loggedHour.getTimeSpent())
              .build();

            loggedTasks.add(loggedTask);

          }


          TaskResponseDTO.LoggedTask loggedTask = TaskResponseDTO.LoggedTask
            .builder()
            .build();


          return TaskResponseDTO
            .builder()
            .status(task.getStatus())
            .endDate(task.getEndDate())
            .startDate(task.getStartDate())
            .title(task.getTitle())
            .taskCategory(categoryOfTask)
            .id(task.getTaskId())
            .assignee(task1)
            .description(task.getDescription())
            .loggedHour(loggedTasks)
            .build();

        }
      )
      .toList();
  }

  public List<TaskResponseDTO> getAllTask() {


    List<TaskEntity> tasks = taskRepository.findAll(Sort.by("startDate").descending());


    return tasks.stream()
      .map(task -> {

          UserEntity userEntity = task.getAssignee();

        TaskResponseDTO.UserTask userTask = null;

          if(userEntity != null){
            userTask = TaskResponseDTO
              .UserTask
              .builder()
              .userId(userEntity.getUserId())
              .name(userEntity.getName())
              .email(userEntity.getEmail())
              .role(userEntity.getRole())
              .build();

          }

          return TaskResponseDTO
            .builder()
            .status(task.getStatus())
            .endDate(task.getEndDate())
            .startDate(task.getStartDate())
            .title(task.getTitle())
            .id(task.getTaskId())
            .assignee(userTask)
            .build();
        }
      )
      .toList();
  }

  @Transactional
  public Boolean updateTask(TaskRequestDTO taskDTO, Long taskId) throws EntityNotFoundException {

    TaskEntity task = taskRepository.findById(taskId)
      .orElseThrow(() -> new EntityNotFoundException("task with id " + taskId + "cannot be found"));

    if (Objects.nonNull(taskDTO.description())) {
      task.setDescription(taskDTO.description());
    }

    if (Objects.nonNull(taskDTO.title())) {
      task.setTitle(taskDTO.title());
    }

    if (Objects.nonNull(taskDTO.status())) {
      task.setStatus(taskDTO.status());
    }

    if (Objects.nonNull(taskDTO.assignee())) {
      task.setAssignee(taskDTO.assignee());
    }

    if (Objects.nonNull(taskDTO.taskCategory())) {
      task.setTaskCategory(taskDTO.taskCategory());
    }

    if (Objects.nonNull(taskDTO.endDate())) {
      task.setEndDate(taskDTO.endDate());
    }

    if (Objects.nonNull(taskDTO.startDate())) {
      task.setStartDate(taskDTO.startDate());
    }

    TaskEntity save = taskRepository.save(task);
    return true;
  }

  @Transactional
  public Boolean removeTask(Long taskId) {
    taskRepository.deleteById(taskId);
    return true;
  }

  public List<TaskResponseDTO> taskByStatus(String status) {
    List<TaskEntity> tasks = taskRepository.findByStatusOrderByTitleAsc(StatusEnum.valueOf(status));

    return tasks
      .stream()
      .map(task -> TaskResponseDTO
        .builder()
        .status(task.getStatus())
        .endDate(task.getEndDate())
        .startDate(task.getStartDate())
        .id(task.getTaskId())
        .build()
      )
      .toList();

  }

  public List<TaskResponseDTO> filterAssignedTaskByStatus(String status) {
    List<TaskEntity> tasks = taskRepository.taskByUserAndStatus(StatusEnum.valueOf(status));

    return tasks.stream()
      .map(task -> TaskResponseDTO
        .builder()
        .status(task.getStatus())
        .endDate(task.getEndDate())
        .startDate(task.getStartDate())
        .id(task.getTaskId())
        .build()
      )
      .toList();
  }
}
