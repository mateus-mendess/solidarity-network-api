package com.solidarity.api.mapper;

import com.solidarity.api.dto.response.CategoryResponse;
import com.solidarity.api.model.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> categoryList);
}
