package com.solidarity.api.model.service;

import com.solidarity.api.dto.response.CategoryResponse;
import com.solidarity.api.mapper.CategoryMapper;
import com.solidarity.api.model.entity.Category;
import com.solidarity.api.model.repository.CategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryDAO categoryDAO, CategoryMapper categoryMapper) {
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toCategoryResponseList(categoryDAO.findAll());
    }
}
