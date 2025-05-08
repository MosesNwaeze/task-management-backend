package com.softnet.task_management.web.controllers.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record AuthRequestDTO(
  @NotBlank
  @Email
  String email,
  @NotBlank String password
) {
}
