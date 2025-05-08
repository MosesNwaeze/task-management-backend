package com.softnet.task_management.web.controllers.category;

import com.softnet.task_management.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryResponseDTO(
  @NotNull
  Long id,
  @NotBlank
  String name,
  @NotBlank
  String description,
  CreatedBy createdBy
) {

  @Builder
  public record CreatedBy(
    Long id,
    String name,
    String email,
    RoleEnum role
  ) {
  }
}
