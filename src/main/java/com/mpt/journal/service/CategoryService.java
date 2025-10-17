package com.mpt.journal.service;

import com.mpt.journal.model.CategoryModel;
import com.mpt.journal.repository.InMemoryCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final InMemoryCategoryRepository categoryRepository;

    public CategoryService(InMemoryCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        initializeSampleCategories();
    }

    private void initializeSampleCategories() {
        addCategory(new CategoryModel(0, "Подвесные", "Унитазы с подвесной установкой"));
        addCategory(new CategoryModel(0, "Напольные", "Классические напольные унитазы"));
        addCategory(new CategoryModel(0, "Угловые", "Унитазы для угловой установки"));
        addCategory(new CategoryModel(0, "Компактные", "Компактные модели для маленьких помещений"));
        addCategory(new CategoryModel(0, "Биде", "Унитазы-биде с функцией гигиены"));
    }

    public List<CategoryModel> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public List<CategoryModel> findActiveCategories() {
        return categoryRepository.findActiveCategories();
    }

    public CategoryModel findCategoryById(int id) {
        return categoryRepository.findCategoryById(id);
    }

    public CategoryModel findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public CategoryModel addCategory(CategoryModel category) {
        return categoryRepository.addCategory(category);
    }

    public CategoryModel updateCategory(CategoryModel category) {
        return categoryRepository.updateCategory(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteCategory(id);
    }

    public boolean softDeleteCategory(int id) {
        return categoryRepository.softDeleteCategory(id);
    }

    public List<String> getAllCategoryNames() {
        return categoryRepository.getAllCategoryNames().stream().sorted().collect(Collectors.toList());
    }
}