package com.txy.blog.service;

import com.txy.blog.entity.Category;
import com.txy.blog.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategoryById(Long id, CategoryDTO categoryDTO);

    void deleteCategoryById(Long id);
}
