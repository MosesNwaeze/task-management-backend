package com.softnet.task_management.web.controllers.category;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.enums.RoleEnum;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryRequestDTO(
  @NotBlank String name,
  @NotBlank String description,
  UserEntity user,
  List<TaskEntity> tasks
) {
}
