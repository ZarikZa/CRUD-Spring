package com.mpt.journal.controller;
import com.mpt.journal.model.CategoryModel;
import com.mpt.journal.model.ToiletModel;
import com.mpt.journal.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping("/toilets")
    public String getAllToilets(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String categoryName,
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
        } else if (brand != null && !brand.isEmpty() || maxPriceValue != null || categoryName != null && !categoryName.isEmpty()) {
            sourceList = toiletService.filterToilets(brand, maxPriceValue, categoryName);
            totalCount = sourceList.size();
        } else {
            sourceList = toiletService.findActiveToilets();
            totalCount = sourceList.size();
        }

        List<ToiletModel> toilets = toiletService.getToiletsWithPagination(page, size, sourceList);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("toilets", toilets);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("searchTerm", search);
        model.addAttribute("filterBrand", brand);
        model.addAttribute("filterMaxPrice", maxPriceValue);
        model.addAttribute("filterCategory", categoryName);
        model.addAttribute("brands", toiletService.getAllBrands());
        model.addAttribute("categories", toiletService.getAllCategories());

        return "toiletList";
    }

    @GetMapping("/toilets/add")
    public String showAddForm(Model model) {
        model.addAttribute("toilet", new ToiletModel());
        model.addAttribute("categories", toiletService.getAllCategories());
        return "addToilet";
    }

    @PostMapping("/toilets/add")
    public String addToilet(@ModelAttribute ToiletModel toilet,
                            @RequestParam int categoryId) {
        CategoryModel category = toiletService.findCategoryById(categoryId);
        toilet.setCategory(category);
        toiletService.addToilet(toilet);
        return "redirect:/toilets";
    }

    @GetMapping("/toilets/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        ToiletModel toilet = toiletService.findToiletById(id);
        if (toilet == null || !toilet.isActive()) {
            return "redirect:/toilets";
        }
        model.addAttribute("toilet", toilet);
        model.addAttribute("categories", toiletService.getAllCategories());
        return "editToilet";
    }

    @PostMapping("/toilets/edit")
    public String updateToilet(@ModelAttribute ToiletModel toilet,
                               @RequestParam int categoryId) {
        CategoryModel category = toiletService.findCategoryById(categoryId);
        toilet.setCategory(category);
        toiletService.updateToilet(toilet);
        return "redirect:/toilets";
    }

    @GetMapping("/toilets/delete/{id}")
    public String showDeleteForm(@PathVariable int id, Model model) {
        ToiletModel toilet = toiletService.findToiletById(id);
        if (toilet == null || !toilet.isActive()) {
            return "redirect:/toilets";
        }
        model.addAttribute("toilet", toilet);
        return "deleteToilet";
    }

    @PostMapping("/toilets/delete")
    public String deleteToilet(@RequestParam int id) {
        toiletService.deleteToilet(id);
        return "redirect:/toilets";
    }

    @GetMapping("/toilets/soft-delete/{id}")
    public String showSoftDeleteForm(@PathVariable int id, Model model) {
        ToiletModel toilet = toiletService.findToiletById(id);
        if (toilet == null || !toilet.isActive()) {
            return "redirect:/toilets";
        }
        model.addAttribute("toilet", toilet);
        return "softDeleteToilet";
    }

    @PostMapping("/toilets/soft-delete")
    public String softDeleteToilet(@RequestParam int id) {
        toiletService.softDeleteToilet(id);
        return "redirect:/toilets";
    }

    @GetMapping("/toilets/multiple-delete")
    public String showMultipleDeleteForm(Model model) {
        List<ToiletModel> activeToilets = toiletService.findActiveToilets();
        model.addAttribute("toilets", activeToilets);
        return "multipleDelete";
    }

    @PostMapping("/toilets/multiple-delete")
    public String deleteMultipleToilets(@RequestParam("ids") List<Integer> ids) {
        toiletService.deleteMultipleToilets(ids);
        return "redirect:/toilets";
    }

    @GetMapping("/toilets/multiple-soft-delete")
    public String showMultipleSoftDeleteForm(Model model) {
        List<ToiletModel> activeToilets = toiletService.findActiveToilets();
        model.addAttribute("toilets", activeToilets);
        return "multipleSoftDelete";
    }

    @PostMapping("/toilets/multiple-soft-delete")
    public String softDeleteMultipleToilets(@RequestParam("ids") List<Integer> ids) {
        toiletService.softDeleteMultipleToilets(ids);
        return "redirect:/toilets";
    }
}