package com.softnet.task_management.domains.logging;

import com.softnet.task_management.domains.task.TaskEntity;
import com.softnet.task_management.domains.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggedHourRepository extends JpaRepository<LoggedHourEntity, Long> {

  List<LoggedHourEntity> findAllByUserOrderByDateLoggedDesc(UserEntity user);

  List<LoggedHourEntity> findByTasksOrderByDateLoggedDesc(TaskEntity tasks);


}
