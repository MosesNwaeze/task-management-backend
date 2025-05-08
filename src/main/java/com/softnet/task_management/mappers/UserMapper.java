package com.softnet.task_management.mappers;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import com.softnet.task_management.web.dtos.LoggedInHourResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

  private UserMapper() {
  }

  public static UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
    List<LoggedInHourResponseDTO> loggedHour = mapLoggedHours(userEntity.getLoggedHour());
    List<TaskResponseDTO> taskResponseDTOList = mapTasks(userEntity.getTasks());
    List<UserResponseDTO.UserAssignedTask> userAssignedTasks = mapAssignedTasks(userEntity.getTasks());

    return UserResponseDTO.builder()
      .id(userEntity.getUserId())
      .role(userEntity.getRole())
      .name(userEntity.getName())
      .email(userEntity.getEmail())
      .tasks(userAssignedTasks)
      .build();
  }

  private static List<LoggedInHourResponseDTO> mapLoggedHours(List<LoggedHourEntity> loggedHourEntities) {
    List<LoggedInHourResponseDTO> loggedHours = new ArrayList<>();
    if (loggedHourEntities != null) {
      for (LoggedHourEntity logged : loggedHourEntities) {
        loggedHours.add(LoggedInHourResponseDTO.builder()
          .dateLogged(logged.getDateLogged())
          .id(logged.getLoggedHourId())
          .timeSpent(logged.getTimeSpent())
          .build());
      }
    }
    return loggedHours;
  }

  private static List<TaskResponseDTO> mapTasks(List<TaskEntity> tasks) {
    List<TaskResponseDTO> taskResponses = new ArrayList<>();
    if (tasks != null) {
      for (TaskEntity task : tasks) {
        List<TaskResponseDTO.LoggedTask> loggedTasks = mapLoggedTasks(task.getLoggedHour());
        TaskResponseDTO.CategoryOfTask category = mapCategoryOfTask(task.getTaskCategory());

        taskResponses.add(TaskResponseDTO.builder()
          .status(task.getStatus())
          .startDate(task.getStartDate())
          .endDate(task.getEndDate())
          .id(task.getTaskId())
          .title(task.getTitle())
          .description(task.getDescription())
          .loggedHour(loggedTasks)
          .taskCategory(category)
          .build());
      }
    }
    return taskResponses;
  }

  private static List<TaskResponseDTO.LoggedTask> mapLoggedTasks(List<LoggedHourEntity> loggedHours) {
    List<TaskResponseDTO.LoggedTask> loggedTaskList = new ArrayList<>();
    if (loggedHours != null) {
      for (LoggedHourEntity taskLogged : loggedHours) {
        loggedTaskList.add(TaskResponseDTO.LoggedTask.builder()
          .dateLogged(taskLogged.getDateLogged())
          .loggedHourId(taskLogged.getLoggedHourId())
          .timeSpent(taskLogged.getTimeSpent())
          .build());
      }
    }
    return loggedTaskList;
  }

  private static TaskResponseDTO.CategoryOfTask mapCategoryOfTask(TaskCategoryEntity categoryEntity) {
    if (categoryEntity == null) return null;
    return TaskResponseDTO.CategoryOfTask.builder()
      .categoryId(categoryEntity.getCategoryId())
      .description(categoryEntity.getDescription())
      .name(categoryEntity.getName())
      .build();
  }

  private static List<UserResponseDTO.UserAssignedTask> mapAssignedTasks(List<TaskEntity> tasks) {
    List<UserResponseDTO.UserAssignedTask> assignedTasks = new ArrayList<>();
    if (tasks != null) {
      for (TaskEntity task : tasks) {
        List<UserResponseDTO.UserAssignedTask.UserCreatedTaskCategory> categories = new ArrayList<>();
        TaskCategoryEntity taskCategory = task.getTaskCategory();
        if (taskCategory != null) {
          categories.add(UserResponseDTO.UserAssignedTask.UserCreatedTaskCategory.builder()
            .categoryId(taskCategory.getCategoryId())
            .description(taskCategory.getDescription())
            .name(taskCategory.getName())
            .build());
        }

        assignedTasks.add(UserResponseDTO.UserAssignedTask.builder()
          .id(task.getTaskId())
          .startDate(task.getStartDate())
          .status(task.getStatus())
          .description(task.getDescription())
          .taskCategories(categories)
          .build());
      }
    }
    return assignedTasks;
  }
}

