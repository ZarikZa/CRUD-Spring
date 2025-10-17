package com.mpt.journal.controller;
import com.mpt.journal.model.CategoryModel;
import com.mpt.journal.model.ToiletModel;
import com.mpt.journal.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/toilets")
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping
    public String getAllToilets(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) Integer categoryId,
            Model model) {

        List<ToiletModel> sourceList;
        long totalCount;

        Double maxPriceValue = null;
        if (maxPrice != null && !maxPrice.isEmpty() && !"null".equals(maxPrice)) {
            try {
                maxPriceValue = Double.parseDouble(maxPrice);
            } catch (NumberFormatException e) {
            }
        }

        if (search != null && !search.isEmpty()) {
            sourceList = toiletService.searchToilets(search);
            totalCount = sourceList.size();
        } else if (brand != null && !brand.isEmpty() || maxPriceValue != null || categoryId != null) {
            sourceList = toiletService.filterToilets(brand, maxPriceValue, categoryId);
            totalCount = sourceList.size();
        } else {
            sourceList = toiletService.findAllToilets();
            totalCount = sourceList.size();
        }

        List<ToiletModel> toilets = toiletService.getToiletsWithPagination(page, size, sourceList);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        Map<Integer, String> categoryNames = new HashMap<>();
        for (ToiletModel toilet : toilets) {
            if (!categoryNames.containsKey(toilet.getCategoryId())) {
                String categoryName = toiletService.getCategoryNameById(toilet.getCategoryId());
                categoryNames.put(toilet.getCategoryId(), categoryName);
            }
        }

        model.addAttribute("toilets", toilets);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("searchTerm", search);
        model.addAttribute("filterBrand", brand);
        model.addAttribute("filterMaxPrice", maxPriceValue);
        model.addAttribute("filterCategoryId", categoryId);
        model.addAttribute("brands", toiletService.getAllBrands());
        model.addAttribute("categories", toiletService.getAllCategories());
        model.addAttribute("categoryNames", categoryNames);

        return "toilets";
    }

    @PostMapping("/add")
    public String addToilet(@RequestParam String name,
                            @RequestParam String brand,
                            @RequestParam String modelName,
                            @RequestParam String color,
                            @RequestParam double price,
                            @RequestParam int stockQuantity,
                            @RequestParam String material,
                            @RequestParam int categoryId,
                            @RequestParam(defaultValue = "false") boolean waterSaving) {

        ToiletModel toilet = new ToiletModel(0, name, brand, modelName, color,
                price, stockQuantity, material, categoryId, waterSaving);
        toiletService.addToilet(toilet);
        return "redirect:/toilets";
    }

    @PostMapping("/update")
    public String updateToilet(@RequestParam int id,
                               @RequestParam String name,
                               @RequestParam String brand,
                               @RequestParam String model,
                               @RequestParam String color,
                               @RequestParam double price,
                               @RequestParam int stockQuantity,
                               @RequestParam String material,
                               @RequestParam int categoryId,
                               @RequestParam(defaultValue = "false") boolean waterSaving) {

        ToiletModel toilet = new ToiletModel(id, name, brand, model, color,
                price, stockQuantity, material, categoryId, waterSaving);
        toiletService.updateToilet(toilet);
        return "redirect:/toilets";
    }

    @PostMapping("/delete")
    public String deleteToilet(@RequestParam int id) {
        toiletService.deleteToilet(id);
        return "redirect:/toilets";
    }

    @PostMapping("/soft-delete")
    public String softDeleteToilet(@RequestParam int id) {
        toiletService.softDeleteToilet(id);
        return "redirect:/toilets";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultipleToilets(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        System.out.println("DELETE MULTIPLE: " + ids);
        if (ids != null && !ids.isEmpty()) {
            toiletService.deleteMultipleToilets(ids);
        }
        return "redirect:/toilets";
    }

    @PostMapping("/soft-delete-multiple")
    public String softDeleteMultipleToilets(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        System.out.println("SOFT DELETE MULTIPLE: " + ids);
        if (ids != null && !ids.isEmpty()) {
            toiletService.softDeleteMultipleToilets(ids);
        }
        return "redirect:/toilets";
    }
}