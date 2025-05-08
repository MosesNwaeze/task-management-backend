package com.softnet.task_management.web.controllers.user;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
public record UserRequestDTO(
  @NotBlank
  String name,
  @Email
  @NotBlank
  String email,
  @NotBlank
  String password,
  @NotNull
  RoleEnum role,
  List<LoggedHourEntity> loggedHours,
  List<TaskEntity> tasks,
  List<TaskCategoryEntity> taskCategoryEntities

) {
  public UserRequestDTO(
    String name,
    String email,
    String password,
    RoleEnum role,
    List<LoggedHourEntity> loggedHours,
    List<TaskEntity> tasks,
    List<TaskCategoryEntity> taskCategoryEntities
  ) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.loggedHours = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.taskCategoryEntities = new ArrayList<>();
  }


}
