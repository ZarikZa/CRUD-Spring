package com.mpt.journal.controller;

import com.mpt.journal.model.CategoryModel;
import com.mpt.journal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            Model model) {

        List<CategoryModel> sourceList;
        long totalCount;

        if (search != null && !search.isEmpty()) {
            sourceList = categoryService.searchCategories(search);
            totalCount = sourceList.size();
        } else {
            sourceList = categoryService.findActiveCategories();
            totalCount = sourceList.size();
        }

        List<CategoryModel> categories = categoryService.getCategoriesWithPagination(page, size, sourceList);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("searchTerm", search);

        return "categories";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name,
                              @RequestParam String description) {
        CategoryModel category = new CategoryModel(0, name, description);
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/update")
    public String updateCategory(@RequestParam int id,
                                 @RequestParam String name,
                                 @RequestParam String description) {
        CategoryModel category = new CategoryModel(id, name, description);
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam int id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }

    @PostMapping("/soft-delete")
    public String softDeleteCategory(@RequestParam int id) {
        categoryService.softDeleteCategory(id);
        return "redirect:/categories";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultipleCategories(@RequestParam(value = "ids", required = false) String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<Integer> idList = Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            categoryService.deleteMultipleCategories(idList);
        }
        return "redirect:/categories";
    }

    @PostMapping("/soft-delete-multiple")
    public String softDeleteMultipleCategories(@RequestParam(value = "ids", required = false) String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<Integer> idList = Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            categoryService.softDeleteMultipleCategories(idList);
        }
        return "redirect:/categories";
    }
}