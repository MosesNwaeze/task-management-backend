package com.softnet.task_management.utils;

import com.softnet.task_management.domains.task.TaskEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class TaskLoggedHourDTO {

  //  private LocalDateTime startDate;
//  private LocalDateTime endDate;
//  private StatusEnum status;
//  private LocalDateTime dateLogged;
//  private int timeSpent;
  private List<TaskEntity> taskEntityList;

//  public TaskLoggedHourDTO(
//    LocalDateTime startDate, LocalDateTime endDate, StatusEnum status, LocalDateTime dateLogged, int timeSpent) {
//
//    this.dateLogged = dateLogged;
//    this.startDate = startDate;
//    this.endDate = endDate;
//    this.status = status;
//    this.timeSpent = timeSpent;
//  }

  public TaskLoggedHourDTO(List<TaskEntity> taskEntityList) {
    this.taskEntityList = taskEntityList;
  }

}
