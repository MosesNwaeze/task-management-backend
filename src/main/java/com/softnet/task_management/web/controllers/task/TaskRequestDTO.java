package com.softnet.task_management.web.controllers.task;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TaskRequestDTO(
  @NotBlank
  String title,
  @NotBlank
  String description,
  @NotNull
  LocalDateTime startDate,
  @NotNull
  LocalDateTime endDate,
  @NotNull
  StatusEnum status,
  UserEntity assignee,
  TaskCategoryEntity taskCategory,
  List<LoggedHourEntity> loggedHour

) {

}
