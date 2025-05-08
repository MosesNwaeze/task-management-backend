package com.softnet.task_management.web.controllers.task;

import com.softnet.task_management.domains.logging.LoggedHourService;
import com.softnet.task_management.domains.task.TaskService;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.mappers.EntityToDTOMapper;
import com.softnet.task_management.utils.CustomUser;
import com.softnet.task_management.web.dtos.LoggedInHourRequestDTO;
import com.softnet.task_management.web.dtos.LoggedInHourResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final LoggedHourService loggedHourService;

  @PostMapping("/{cateId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO task, @PathVariable("cateId") Long cateId) throws EntityNotFoundException {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task, cateId));
  }

  @PutMapping("/{taskId}/assign/{userId}")// CHECK THE auth in this methods
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Boolean> assignTaskToUser(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId) throws EntityNotFoundException {

    return ResponseEntity.ok().body(taskService.assignTaskToUser(taskId, userId));

  }

  @GetMapping("/assigned")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<TaskResponseDTO>> taskAssigned() throws EntityNotFoundException {
    CustomUser user = loggedHourService.userDetails();
    return ResponseEntity.ok().body(taskService.taskAssigned(user.getUsername()));
  }


  @GetMapping("/{taskId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<TaskResponseDTO> task(@PathVariable Long taskId) {
    TaskResponseDTO taskResponseDTO = EntityToDTOMapper.taskResponseDTO(taskService.taskById(taskId));
    return ResponseEntity.ok().body(taskResponseDTO);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<TaskResponseDTO>> getAllTask() {
    return ResponseEntity.ok().body(taskService.getAllTask());
  }

  @PutMapping("/{taskId}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<Boolean> updateTask(@RequestBody TaskRequestDTO taskDTO, @PathVariable("taskId") Long taskId) throws EntityNotFoundException {
    return ResponseEntity.ok().body(taskService.updateTask(taskDTO, taskId));
  }


  @DeleteMapping("/{taskId}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<Boolean> removeTask(@PathVariable("taskId") Long taskId) {
    return ResponseEntity.ok().body(taskService.removeTask(taskId));
  }

  @GetMapping("/search")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<TaskResponseDTO>> filterTaskByStatus(
    @RequestParam(value = "status", defaultValue = "DONE") String status) {
    return ResponseEntity.ok().body(taskService.taskByStatus(status));
  }

  @GetMapping("/task-assigned")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<TaskResponseDTO>> filterAssignedTaskByStatus(
    @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status
  ) {
    return ResponseEntity.ok().body(taskService.filterAssignedTaskByStatus(status));
  }

  @PostMapping("/{taskId}/log-hour")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<LoggedInHourResponseDTO> logHour(
    @Valid @RequestBody LoggedInHourRequestDTO log, @PathVariable("taskId") Long taskId) throws EntityNotFoundException {

    return ResponseEntity.status(HttpStatus.CREATED).body(loggedHourService.logHour(log, taskId));

  }


  @GetMapping("/{taskId}/log-hours")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<LoggedInHourResponseDTO>> logHours(@PathVariable("taskId") Long taskId) {

    return ResponseEntity.ok().body(loggedHourService.logHours(taskId));

  }


}
