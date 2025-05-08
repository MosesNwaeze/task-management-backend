package com.softnet.task_management.domains.task;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"taskCategory", "loggedHour", "assignee"})
@Builder
public class TaskEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, name = "task_id")
  private Long taskId;

  @Column(nullable = false, name = "task_title")
  private String title;

  @Column(nullable = false, name = "task_description")
  private String description;

  @Column(nullable = false, name = "task_start_date")
  private LocalDateTime startDate;

  @Column(nullable = false, name = "task_end_date")
  private LocalDateTime endDate;

  @Column(nullable = false, name = "task_status")
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @ManyToOne
  @JoinColumn(name = "assignee")
  private UserEntity assignee;

  @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
  private List<LoggedHourEntity> loggedHour = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "task_category_id")
  private TaskCategoryEntity taskCategory;

}
