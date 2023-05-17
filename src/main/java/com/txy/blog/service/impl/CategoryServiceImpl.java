package com.txy.blog.service.impl;

import com.txy.blog.entity.Category;
import com.txy.blog.exception.ResourceNotFoundException;
import com.txy.blog.payload.CategoryDTO;
import com.txy.blog.repository.CategoryRepository;
import com.txy.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(id)));
        return category;
    }

    @Override
    public Category updateCategoryById(Long id, CategoryDTO categoryDTO) {
        Category category = getCategoryById(id);

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updatedPost = updateCategory(category);

        return updatedPost;
    }

    @Override
    public void deleteCategoryById(Long id) {
        getCategoryById(id);
        categoryRepository.deleteById(id);
    }

    private Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
}
