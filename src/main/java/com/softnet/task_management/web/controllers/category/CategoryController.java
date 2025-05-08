package com.softnet.task_management.web.controllers.category;

import com.softnet.task_management.domains.task_category.TaskCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final TaskCategoryService taskCategoryService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO request) {

    return ResponseEntity.status(HttpStatus.CREATED).body(taskCategoryService.createCategory(request));

  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<CategoryResponseDTO>> fetchCategories() {

    List<CategoryResponseDTO> categories = taskCategoryService.fetchCategories();

    return ResponseEntity.ok().body(categories);

  }

  @DeleteMapping("/{catId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Boolean> removeCategories(@PathVariable Long catId) throws Exception {

    taskCategoryService.removeCategories(catId);

    return ResponseEntity.noContent().build();

  }


  @PutMapping("/{cateId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> updateCategory(@PathVariable("cateId") Long cateId, @RequestBody CategoryRequestDTO categoryRequestDTO){

    this.taskCategoryService.updateCategory(categoryRequestDTO, cateId);
    return ResponseEntity.noContent().build();
  }


}
