package com.softnet.task_management.domains.task_category;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"taskEntity", "createdBy"})
@Builder
public class TaskCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, name = "task_category_id")
  private Long categoryId;

  @Column(name = "task_category_name", nullable = false)
  private String name;

  @Column(name = "task_category_description", nullable = false)
  private String description;

  @OneToMany(mappedBy = "taskCategory")
  private List<TaskEntity> taskEntity = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "task_category_app_user_id")
  private UserEntity createdBy;

}
