package com.mpt.journal.repository;


import com.mpt.journal.model.CategoryModel;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoryRepository {
    private List<CategoryModel> categories = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public CategoryModel addCategory(CategoryModel category) {
        category.setId(idCounter.getAndIncrement());
        categories.add(category);
        return category;
    }

    public CategoryModel updateCategory(CategoryModel category) {
        return categories.stream()
                .filter(c -> c.getId() == category.getId())
                .findFirst()
                .map(c -> {
                    c.setName(category.getName());
                    c.setDescription(category.getDescription());
                    return c;
                })
                .orElse(null);
    }

    public void deleteCategory(int id) {
        categories.removeIf(category -> category.getId() == id);
    }

    public boolean softDeleteCategory(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id && !category.isIsDeleted())
                .findFirst()
                .map(category -> {
                    category.setIsDeleted(true);
                    return true;
                })
                .orElse(false);
    }

    public void deleteMultipleCategories(List<Integer> ids) {
        categories.removeIf(category -> ids.contains(category.getId()));
    }

    public void softDeleteMultipleCategories(List<Integer> ids) {
        categories.stream()
                .filter(category -> ids.contains(category.getId()) && !category.isIsDeleted())
                .forEach(category -> category.setIsDeleted(true));
    }

    public List<CategoryModel> findAllCategories() {
        return new ArrayList<>(categories);
    }

    public List<CategoryModel> findActiveCategories() {
        return categories.stream()
                .filter(category -> !category.isIsDeleted())
                .collect(Collectors.toList());
    }

    public CategoryModel findCategoryById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public CategoryModel findCategoryByName(String name) {
        return categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(name) && !category.isIsDeleted())
                .findFirst()
                .orElse(null);
    }

    public List<CategoryModel> searchCategories(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findActiveCategories();
        }

        String term = searchTerm.toLowerCase();
        return categories.stream()
                .filter(category -> !category.isIsDeleted())
                .filter(category ->
                        category.getName().toLowerCase().contains(term) ||
                                category.getDescription().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    public List<CategoryModel> findCategoriesWithPagination(int page, int size, List<CategoryModel> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = (page - 1) * size;
        if (fromIndex >= sourceList.size()) {
            return new ArrayList<>();
        }

        int toIndex = Math.min(fromIndex + size, sourceList.size());
        return sourceList.subList(fromIndex, toIndex);
    }

    public long getTotalCount(List<CategoryModel> sourceList) {
        return sourceList.size();
    }
}