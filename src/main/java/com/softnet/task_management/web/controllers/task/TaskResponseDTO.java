package com.softnet.task_management.web.controllers.task;

import com.softnet.task_management.enums.RoleEnum;
import com.softnet.task_management.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TaskResponseDTO(
  @NotNull
  Long id,
  @NotNull
  LocalDateTime startDate,
  @NotNull
  LocalDateTime endDate,
  @NotNull
  StatusEnum status,
  @NotBlank String title,
  @NotBlank String description,
  List<LoggedTask> loggedHour,
  CategoryOfTask taskCategory,
  UserTask assignee
) {

  @Builder
  public record UserTask(
    Long userId,
    String name,
    String email,
    String password,
    RoleEnum role
  ) {
  }

  @Builder
  public record LoggedTask(
    Long loggedHourId,
    LocalDateTime dateLogged,
    int timeSpent

  ) {
  }

  @Builder
  public record CategoryOfTask(
    Long categoryId,
    String name,
    String description

  ) {
  }

}
