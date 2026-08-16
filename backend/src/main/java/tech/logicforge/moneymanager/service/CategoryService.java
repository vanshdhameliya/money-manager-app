package tech.logicforge.moneymanager.service;

import tech.logicforge.moneymanager.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);


    List<CategoryDto> getCategoriesForCurrentUser();


    List<CategoryDto> getCategoriesByTypeForCurrentUser(String type);

    CategoryDto updateById(Long categoryId, CategoryDto categoryDto);
}
