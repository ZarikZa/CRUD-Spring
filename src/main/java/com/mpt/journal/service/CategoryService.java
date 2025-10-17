package com.mpt.journal.service;

import com.mpt.journal.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    List<CategoryModel> findAllCategories();
    List<CategoryModel> findActiveCategories();
    CategoryModel findCategoryById(int id);
    CategoryModel findCategoryByName(String name);
    CategoryModel addCategory(CategoryModel category);
    CategoryModel updateCategory(CategoryModel category);
    void deleteCategory(int id);
    boolean softDeleteCategory(int id);
    void deleteMultipleCategories(List<Integer> ids);
    void softDeleteMultipleCategories(List<Integer> ids);
    List<CategoryModel> searchCategories(String searchTerm);
    List<CategoryModel> getCategoriesWithPagination(int page, int size, List<CategoryModel> sourceList);
    long getTotalCategoriesCount(List<CategoryModel> sourceList);
}