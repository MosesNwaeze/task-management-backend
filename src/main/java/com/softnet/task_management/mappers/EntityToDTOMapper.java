package com.softnet.task_management.mappers;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.web.controllers.category.CategoryResponseDTO;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import com.softnet.task_management.web.dtos.LoggedInHourResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityToDTOMapper {

  private EntityToDTOMapper() {
  }

  public static TaskResponseDTO taskResponseDTO(TaskEntity taskEntity) {

    List<LoggedHourEntity> loggedHourEntityList = taskEntity.getLoggedHour();
    UserEntity userEntity = taskEntity.getAssignee();
    TaskCategoryEntity taskCategoryEntity = taskEntity.getTaskCategory();
    TaskResponseDTO.CategoryOfTask taskResponseDTO = null;
    TaskResponseDTO.UserTask userTask = null;
    List<TaskResponseDTO.LoggedTask> loggedTasks = new ArrayList<>();

    if (userEntity != null) {
      userTask = TaskResponseDTO
        .UserTask
        .builder()
        .userId(userEntity.getUserId())
        .role(userEntity.getRole())
        .name(userEntity.getName())
        .email(userEntity.getEmail())
        .build();

    }

    if (taskCategoryEntity != null) {

      taskResponseDTO = TaskResponseDTO
        .CategoryOfTask
        .builder()
        .description(taskCategoryEntity.getDescription())
        .name(taskCategoryEntity.getName())
        .description(taskEntity.getDescription())

        .build();


    }

    for (LoggedHourEntity loggedHour : loggedHourEntityList) {
      TaskResponseDTO.LoggedTask loggedTask = TaskResponseDTO.LoggedTask
        .builder()
        .timeSpent(loggedHour.getTimeSpent())
        .loggedHourId(loggedHour.getLoggedHourId())
        .dateLogged(loggedHour.getDateLogged())
        .build();
    }


    return TaskResponseDTO
      .builder()
      .id(taskEntity.getTaskId())
      .status(taskEntity.getStatus())
      .startDate(taskEntity.getStartDate())
      .title(taskEntity.getTitle())
      .description(taskEntity.getDescription())
      .endDate(taskEntity.getEndDate())
      .assignee(userTask)
      .taskCategory(taskResponseDTO)
      .loggedHour(loggedTasks)
      .build();
  }

  public static CategoryResponseDTO categoryResponseDTO(TaskCategoryEntity categoryEntity) {
    return CategoryResponseDTO
      .builder()
      .description(categoryEntity.getDescription())
      .id(categoryEntity.getCategoryId())
      .name(categoryEntity.getName())
      .build();
  }


  public static LoggedInHourResponseDTO loggedInHourResponseDTO(LoggedHourEntity loggedHourEntity) {

    return LoggedInHourResponseDTO
      .builder()
      .id(loggedHourEntity.getLoggedHourId())
      .timeSpent(loggedHourEntity.getTimeSpent())
      .dateLogged(loggedHourEntity.getDateLogged())
      .build();
  }

  public static UserResponseDTO userResponseDTO(UserEntity userEntity) {
    return UserMapper.toUserResponseDTO(userEntity);

  }

}
