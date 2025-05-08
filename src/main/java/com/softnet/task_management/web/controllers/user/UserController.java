package com.softnet.task_management.web.controllers.user;

import com.softnet.task_management.domains.user.UserService;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.utils.CustomUser;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<List<UserResponseDTO>> users() {
    return ResponseEntity.ok().body(userService.users());
  }

  @PutMapping("/details")
  @PreAuthorize("hasAnyRole('USER')")
  public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO user) throws EntityNotFoundException {
    CustomUser customUser = userDetails();
    return ResponseEntity.ok().body(userService.updateUser(customUser.getUsername(), user));
  }


  @GetMapping("/details")
  @PreAuthorize("hasAnyRole('USER')")
  public ResponseEntity<UserResponseDTO> currentUser() {

    CustomUser user = userDetails();
    return ResponseEntity.ok().body(userService.userByEmail(user.getUsername()));

  }

  @DeleteMapping("/details")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<Boolean> removeCurrentUser() {
    CustomUser user = userDetails();
    return ResponseEntity.ok().body(userService.removeCurrentUser(user.getUsername()));
  }

  @GetMapping("/{userId}/best-task")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<TaskResponseDTO> bestPerformedTask(@PathVariable("userId") Long userId) {

    return ResponseEntity.ok().body(userService.bestPerformedTask(userId));

  }

  private CustomUser userDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (CustomUser) authentication.getPrincipal();
  }

}
