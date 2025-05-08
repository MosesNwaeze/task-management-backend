package com.softnet.task_management.domains.logging;


import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task.TaskService;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.domains.user.UserService;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.utils.CustomUser;
import com.softnet.task_management.utils.LoggingTimeHelper;
import com.softnet.task_management.web.dtos.LoggedInHourRequestDTO;
import com.softnet.task_management.web.dtos.LoggedInHourResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoggedHourService {

  private final LoggedHourRepository loggedHourRepository;
  private final TaskService taskService;
  private final UserService userService;

  @Transactional
  public LoggedInHourResponseDTO logHour(LoggedInHourRequestDTO log, Long taskId) throws EntityNotFoundException {

    TaskEntity taskEntity = taskService.taskById(taskId);

    CustomUser customUser = userDetails();
    String username = customUser.getUsername();

    UserEntity userEntity = this.userService.email(username);

    LoggedHourEntity loggedHourEntity = LoggedHourEntity
      .builder()
      .timeSpent(LoggingTimeHelper.formatTime(log.timeSpent()))
      .dateLogged(LocalDateTime.now())
      .tasks(taskEntity)
      .user(userEntity)
      .build();

    LoggedHourEntity loggedHourEntity1 = loggedHourRepository.save(loggedHourEntity);

    return LoggedInHourResponseDTO
      .builder()
      .dateLogged(loggedHourEntity1.getDateLogged())
      .id(loggedHourEntity1.getLoggedHourId())
      .timeSpent(loggedHourEntity1.getTimeSpent())
      .build();

  }

  public List<LoggedInHourResponseDTO> logHours(Long taskId) {

    TaskEntity taskEntity = taskService.taskById(taskId);

    List<LoggedHourEntity> loggedHour = loggedHourRepository.findByTasksOrderByDateLoggedDesc(taskEntity);


    return loggedHour.stream()
      .map(task -> {

          TaskEntity taskEntity1 = task.getTasks();
          UserEntity user1 = task.getUser();

          LoggedInHourResponseDTO.TaskLogged taskLogged = null;
          LoggedInHourResponseDTO.UserLogged userLogged = null;

          if (taskEntity1 != null) {
            taskLogged = LoggedInHourResponseDTO
              .TaskLogged
              .builder()
              .taskId(taskEntity1.getTaskId())
              .description(taskEntity1.getDescription())
              .endDate(taskEntity1.getEndDate())
              .startDate(taskEntity1.getStartDate())
              .status(taskEntity1.getStatus())
              .title(taskEntity1.getTitle())
              .build();
          }

          if (user1 != null) {
            userLogged = LoggedInHourResponseDTO
              .UserLogged
              .builder()
              .userId(user1.getUserId())
              .email(user1.getEmail())
              .name(user1.getName())
              .role(user1.getRole())
              .build();
          }


          return LoggedInHourResponseDTO
            .builder()
            .dateLogged(task.getDateLogged())
            .id(task.getLoggedHourId())
            .timeSpent(task.getTimeSpent())
            .tasks(taskLogged)
            .user(userLogged)
            .build();
        }
      )
      .toList();
  }

  public CustomUser userDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (CustomUser) authentication.getPrincipal();
  }
}
