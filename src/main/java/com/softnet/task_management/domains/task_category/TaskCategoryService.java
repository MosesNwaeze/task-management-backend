package com.softnet.task_management.domains.task_category;

import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.utils.CustomUserDetailsService;
import com.softnet.task_management.web.controllers.category.CategoryRequestDTO;
import com.softnet.task_management.web.controllers.category.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskCategoryService {

  private final TaskCategoryRepository taskCategoryRepository;
  private final CustomUserDetailsService customUserDetailsService;


  @Transactional
  public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    UserEntity userEntity = customUserDetailsService.getUserEntity(userDetails.getUsername());

    TaskCategoryEntity categoryEntity = TaskCategoryEntity
      .builder()
      .taskEntity(request.tasks())
      .name(request.name())
      .description(request.description())
      .createdBy(userEntity)
      .build();

    TaskCategoryEntity taskCategory = taskCategoryRepository.save(categoryEntity);

    CategoryResponseDTO.CreatedBy createdBy = new CategoryResponseDTO.CreatedBy(
      userEntity.getUserId(),
      userEntity.getName(),
      userEntity.getEmail(),
      userEntity.getRole()
    );

    return CategoryResponseDTO
      .builder()
      .description(taskCategory.getDescription())
      .name(taskCategory.getName())
      .id(taskCategory.getCategoryId())
      .createdBy(createdBy)
      .build();
  }

  public List<CategoryResponseDTO> fetchCategories() {

    return taskCategoryRepository.findAll()
      .stream()
      .map(cate -> {


          UserEntity userEntity = cate.getCreatedBy();

          CategoryResponseDTO.CreatedBy createdBy = CategoryResponseDTO.CreatedBy
            .builder()
            .email(userEntity.getEmail())
            .role(userEntity.getRole())
            .id(userEntity.getUserId())
            .name(userEntity.getName())
            .build();

          return CategoryResponseDTO
            .builder()
            .name(cate.getName())
            .id(cate.getCategoryId())
            .description(cate.getDescription())
            .createdBy(createdBy)
            .build();
        }
      )
      .toList();
  }

  @Transactional
  public void removeCategories(Long catId) throws Exception {

    try {
      this.taskCategoryRepository.deleteById(catId);
    }catch (Exception ex){
      throw new Exception(ex.getMessage());
    }

  }
}
