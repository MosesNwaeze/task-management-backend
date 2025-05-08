package com.softnet.task_management.domains.user;

import com.softnet.task_management.domains.logging.LoggedHourEntity;
import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.task_category.TaskCategoryEntity;
import com.softnet.task_management.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"tasks", "loggedHour", "taskCategoryEntities"})
@Builder
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, name = "app_user_id")
  private Long userId;

  @Column(nullable = false, name = "app_user_name",unique = true)
  private String name;

  @Column(unique = true, nullable = false, name = "app_user_email")
  private String email;

  @Column(nullable = false, name = "app_user_password")
  private String password;

  @Column(nullable = false, name = "app_user_role")
  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
  private List<TaskEntity> tasks = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<LoggedHourEntity> loggedHour = new ArrayList<>();

  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
  private List<TaskCategoryEntity> taskCategoryEntities = new ArrayList<>();

}
