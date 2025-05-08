package com.softnet.task_management.domains.logging;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "logged_hour")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"tasks", "user"})
@Builder
public class LoggedHourEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "logged_hour_id", nullable = false)
  private Long loggedHourId;

  @Column(nullable = false, name = "date_logged")
  private LocalDateTime dateLogged;

  @Column(name = "time_spent")
  private int timeSpent;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private TaskEntity tasks;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
