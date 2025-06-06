package com.softnet.task_management.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String username);

  boolean existsByEmail(String username);

  void removeByEmail(String email);


  @Query(
    """
      SELECT
      u
      FROM
      UserEntity u
      WHERE u.userId = :userId
      """
  )
  UserEntity task(@Param("userId") Long userId);
}
