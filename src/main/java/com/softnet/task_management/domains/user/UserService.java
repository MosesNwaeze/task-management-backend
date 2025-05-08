package com.softnet.task_management.domains.user;


import com.softnet.task_management.domains.logging.LoggedHourRepository;
import com.softnet.task_management.domains.task.TaskRepository;
import com.softnet.task_management.exception.DuplicateEntityException;
import com.softnet.task_management.exception.EntityNotFoundException;
import com.softnet.task_management.mappers.DTOToEntityMapper;
import com.softnet.task_management.mappers.EntityToDTOMapper;
import com.softnet.task_management.web.controllers.task.TaskResponseDTO;
import com.softnet.task_management.web.controllers.user.UserRequestDTO;
import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final LoggedHourRepository loggedHourRepository;
  private final TaskRepository taskRepository;

  @Transactional
  public UserResponseDTO register(UserRequestDTO userRequestDTO) throws DuplicateEntityException {

    boolean exists = userRepository.existsByEmail(userRequestDTO.email());

    if (!exists) {
      UserEntity userEntity = DTOToEntityMapper.userEntity(userRequestDTO);
      return EntityToDTOMapper.userResponseDTO(userRepository.save(userEntity));
    }

    throw new DuplicateEntityException("email already exists");
  }


  public UserResponseDTO userByEmail(String email) {
    UserEntity userEntity = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("user with email " + email + "Cannot be found"));
    return EntityToDTOMapper.userResponseDTO(userEntity);
  }

  public UserEntity email(String email) throws EntityNotFoundException {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new EntityNotFoundException("user with the email address " + email + " cannot be found"));
  }

  @Transactional
  public UserEntity saveUser(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }


  public UserEntity userById(Long id) throws EntityNotFoundException {
    return userRepository
      .findById(id).orElseThrow(() -> new EntityNotFoundException("User cannot be found"));
  }

  public List<UserResponseDTO> users() {

    return userRepository.findAll()
      .stream()
      .map(user -> UserResponseDTO
        .builder()
        .role(user.getRole())
        .id(user.getUserId())
        .name(user.getName())
        .email(user.getEmail())
        .build()
      )
      .toList();

  }

  @Transactional
  public UserResponseDTO updateUser(String username, UserRequestDTO userRequestDTO) throws EntityNotFoundException {

    UserEntity userEntity = userRepository.findByEmail(username)
      .orElseThrow(() -> new EntityNotFoundException("User with the email " + username + "cannot be found"));

    UserEntity userEntity1 = this.edit(userRequestDTO, userEntity.getUserId());

    return UserResponseDTO
      .builder()
      .email(userEntity1.getEmail())
      .name(userEntity1.getName())
      .id(userEntity1.getUserId())
      .role(userEntity1.getRole())
      .build();

  }

  @Transactional
  public Boolean removeCurrentUser(String username) {
    userRepository.removeByEmail(username);
    return true;
  }

  @Transactional
  public TaskResponseDTO bestPerformedTask(Long userId) {
    UserEntity userEntities = userRepository.task(userId);
    return null;
  }

  @Transactional
  public void removeUser(Long userId) {
    this.userRepository.deleteById(userId);
  }

  @Transactional
  public void editUser(UserRequestDTO userRequestDTO, Long userId) {
    this.edit(userRequestDTO, userId);
  }


  private UserEntity edit(UserRequestDTO userRequestDTO, Long userId) {

    UserEntity userEntity = this.userRepository.findById(userId).orElseThrow();

    if (Objects.nonNull(userRequestDTO.email())) {
      userEntity.setEmail(userRequestDTO.email());
    }
    if (Objects.nonNull(userRequestDTO.role())) {
      userEntity.setRole(userRequestDTO.role());
    }
    if (Objects.nonNull(userRequestDTO.name())) {
      userEntity.setName(userRequestDTO.name());
    }
    if (Objects.nonNull(userRequestDTO.password())) {
      userEntity.setPassword(this.passwordEncoder.encode(userRequestDTO.password()));
    }
    if (Objects.nonNull(userRequestDTO.tasks())) {
      userEntity.setTasks(userRequestDTO.tasks());
    }
    if (Objects.nonNull(userRequestDTO.loggedHours())) {
      userEntity.setLoggedHour(userRequestDTO.loggedHours());
    }
    if (Objects.nonNull(userRequestDTO.taskCategoryEntities())) {
      userEntity.setTaskCategoryEntities(userRequestDTO.taskCategoryEntities());
    }

    return this.userRepository.save(userEntity);

  }
}
