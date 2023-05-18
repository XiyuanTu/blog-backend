package com.txy.blog.controller;

import com.txy.blog.entity.Category;
import com.txy.blog.payload.CategoryDTO;
import com.txy.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add Category")
    @ApiResponse(responseCode = "201", description = "Category added")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.addCategory(categoryDTO);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get All Categories")
    @ApiResponse(responseCode = "200", description = "Categories fetched")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category By Id")
    @ApiResponse(responseCode = "200", description = "Category fetched")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Category By Id")
    @ApiResponse(responseCode = "200", description = "Category updated")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.updateCategoryById(id, categoryDTO);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Delete Category By Id")
    @ApiResponse(responseCode = "200", description = "Category deleted")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
