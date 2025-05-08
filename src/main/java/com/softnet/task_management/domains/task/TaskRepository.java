package com.softnet.task_management.domains.task;

import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  List<TaskEntity> findByAssigneeOrderByStartDateAsc(UserEntity user);

  List<TaskEntity> findByStatusOrderByTitleAsc(StatusEnum status);

  @Query(
    """
       SELECT t FROM TaskEntity t WHERE t.assignee != NULL  AND t.status = :status
      """
  )
  List<TaskEntity> taskByUserAndStatus(@Param("status") StatusEnum status);


}
