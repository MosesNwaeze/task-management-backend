package com.softnet.task_management.web.controllers.auth;


import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthResponseDTO(
  @NotBlank String token,
  @NotNull UserResponseDTO userDetails
) {



}
