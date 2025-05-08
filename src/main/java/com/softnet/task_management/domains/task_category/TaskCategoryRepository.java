package com.softnet.task_management.domains.task_category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface TaskCategoryRepository extends JpaRepository<TaskCategoryEntity, Long> {
}
