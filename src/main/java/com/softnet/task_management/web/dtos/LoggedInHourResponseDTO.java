package com.softnet.task_management.web.dtos;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.enums.RoleEnum;
import com.softnet.task_management.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record LoggedInHourResponseDTO(
  Long id,
  LocalDateTime dateLogged,
  int timeSpent,
  TaskLogged tasks,
  UserLogged user


) {

  @Builder
  public record TaskLogged(
    @NotNull Long taskId,
    @NotBlank String title,
    @NotBlank String description,
    @NotBlank LocalDateTime startDate,
    @NotNull LocalDateTime endDate,
    @NotNull StatusEnum status
  ) {
  }

  @Builder
  public record UserLogged(
    @NotNull Long userId,
    @NotBlank String name,
    @NotBlank String email,
    @NotNull RoleEnum role
  ) {
  }

}
