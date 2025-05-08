package com.softnet.task_management.mappers;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.utils.LoggingTimeHelper;
import com.softnet.task_management.web.controllers.task.TaskRequestDTO;
import com.softnet.task_management.web.controllers.user.UserRequestDTO;
import com.softnet.task_management.web.dtos.LoggedInHourRequestDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DTOToEntityMapper {

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private DTOToEntityMapper(){};

  public static TaskEntity taskResponseDTO(TaskRequestDTO taskRequestDTO) {

    return TaskEntity
      .builder()
      .taskCategory(taskRequestDTO.taskCategory())
      .endDate(taskRequestDTO.endDate())
      .startDate(taskRequestDTO.startDate())
      .assignee(taskRequestDTO.assignee())
      .title(taskRequestDTO.title())
      .status(taskRequestDTO.status())
      .description(taskRequestDTO.description())
      .loggedHour(taskRequestDTO.loggedHour())
      .build();

  }

  public static LoggedHourEntity loggedInHourResponseDTO(LoggedInHourRequestDTO loggedInHourRequestDTO) {

    return LoggedHourEntity
      .builder()
      .dateLogged(loggedInHourRequestDTO.dateLogged())
      .timeSpent(LoggingTimeHelper.formatTime(loggedInHourRequestDTO.timeSpent()))
      .dateLogged(loggedInHourRequestDTO.dateLogged())
      .tasks(loggedInHourRequestDTO.tasks())
      .user(loggedInHourRequestDTO.user())
      .build();
  }

  public static UserEntity userEntity(UserRequestDTO userRequestDTO) {

    return UserEntity
      .builder()
      .role(userRequestDTO.role())
      .name(userRequestDTO.name())
      .loggedHour(userRequestDTO.loggedHours())
      .email(userRequestDTO.email())
      .tasks(userRequestDTO.tasks())
      .password(passwordEncoder.encode(userRequestDTO.password()))
      .taskCategoryEntities(userRequestDTO.taskCategoryEntities())
      .build();
  }

  public static TaskCategoryEntity categoryResponseDTO(TaskCategoryEntity taskCategoryEntity) {
    return TaskCategoryEntity
      .builder()
      .description(taskCategoryEntity.getDescription())
      .createdBy(taskCategoryEntity.getCreatedBy())
      .name(taskCategoryEntity.getName())
      .categoryId(taskCategoryEntity.getCategoryId())
      .taskEntity(taskCategoryEntity.getTaskEntity())
      .name(taskCategoryEntity.getName())
      .build();
  }
}
