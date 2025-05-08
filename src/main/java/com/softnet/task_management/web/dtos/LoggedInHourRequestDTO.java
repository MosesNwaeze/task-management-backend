package com.softnet.task_management.web.dtos;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record LoggedInHourRequestDTO(

  LocalDateTime dateLogged,
  @NotBlank
  String timeSpent,
  TaskEntity tasks,
  UserEntity user
) {
}
