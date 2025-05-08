package com.softnet.task_management.web.controllers.user;

import com.softnet.task_management.enums.RoleEnum;
import com.softnet.task_management.enums.StatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserResponseDTO(
  @NotNull Long id,
  @NotBlank String name,
  @NotBlank @Email String email,
  @NotNull RoleEnum role,
  List<UserAssignedTask> tasks
) {

  @Builder
  public record UserAssignedTask(
    Long id,
    LocalDateTime startDate,
    LocalDateTime endDate,
    StatusEnum status,
    String title,
    String description,
    List<TaskAssigned> tasks,
    List<UserLogged> loggedHours,
    List<UserCreatedTaskCategory> taskCategories
  ) {

    public record TaskAssigned(
      Long taskId,
      String title,
      String description,
      LocalDateTime startDate,
      LocalDateTime endDate,
      StatusEnum status
    ) {
    }

    public record UserLogged(
      Long loggedHourId,
      LocalDateTime dateLogged,
      int timeSpent

    ) {
    }

    @Builder
    public record UserCreatedTaskCategory(
      Long categoryId,
      String name,
      String description

    ) {
    }

  }
}
