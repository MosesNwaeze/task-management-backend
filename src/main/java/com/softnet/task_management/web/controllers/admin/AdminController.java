package com.softnet.task_management.web.controllers.admin;


import com.softnet.task_management.domains.user.UserService;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.utils.CustomUser;
import com.softnet.task_management.web.controllers.user.UserRequestDTO;
import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  @GetMapping("/details")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserResponseDTO> admin() {

    CustomUser user = userDetails();
    return ResponseEntity.ok().body(userService.userByEmail(user.getUsername()));

  }

  @PutMapping("/details")
  public ResponseEntity<UserResponseDTO> updateAdmin(@RequestBody UserRequestDTO userRequestDTO) throws EntityNotFoundException {
    CustomUser user = userDetails();
    return ResponseEntity.ok().body(userService.updateUser(user.getUsername(), userRequestDTO));
  }

  @DeleteMapping("/details")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Boolean> removeAdmin() {
    CustomUser user = userDetails();
    return ResponseEntity.ok().body(userService.removeCurrentUser(user.getUsername()));
  }


  private CustomUser userDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (CustomUser) authentication.getPrincipal();
  }

}
