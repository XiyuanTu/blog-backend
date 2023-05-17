package com.txy.blog.controller;

import com.txy.blog.entity.Category;
import com.txy.blog.payload.CategoryDTO;
import com.txy.blog.service.CategoryService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.addCategory(categoryDTO);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.updateCategoryById(id, categoryDTO);
        CategoryDTO categoryDTOResponse = modelMapper.map(category, CategoryDTO.class);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
