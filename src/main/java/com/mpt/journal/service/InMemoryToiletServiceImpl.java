package com.mpt.journal.service;

import com.mpt.journal.model.ToiletModel;
import com.mpt.journal.model.CategoryModel;
import com.mpt.journal.repository.InMemoryToiletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InMemoryToiletServiceImpl implements ToiletService {

    private final InMemoryToiletRepository toiletRepository;
    private final CategoryService categoryService;

    public InMemoryToiletServiceImpl(InMemoryToiletRepository toiletRepository, CategoryService categoryService) {
        this.toiletRepository = toiletRepository;
        this.categoryService = categoryService;
        initializeSampleData();
    }

    private void initializeSampleData() {
        List<CategoryModel> categories = categoryService.findActiveCategories();

        if (!categories.isEmpty()) {
            addToilet(new ToiletModel(0, "Компакт унитаз", "Roca", "Dama", "Белый",
                    15000.0, 10, "Фаянс", categories.get(0).getId(), true));
            addToilet(new ToiletModel(0, "Подвесной унитаз", "Cersanit", "Style", "Бежевый",
                    25000.0, 5, "Фарфор", categories.get(1).getId(), true));
            addToilet(new ToiletModel(0, "Напольный унитаз", "Vitra", "Compact", "Черный",
                    18000.0, 8, "Фаянс", categories.get(2).getId(), false));

            for (int i = 1; i <= 15; i++) {
                CategoryModel category = categories.get(i % categories.size());
                addToilet(new ToiletModel(0, "Унитаз модель " + i,
                        i % 3 == 0 ? "Roca" : (i % 3 == 1 ? "Cersanit" : "Vitra"),
                        "Model" + i,
                        i % 2 == 0 ? "Белый" : "Бежевый",
                        10000 + i * 1000,
                        5 + i,
                        i % 2 == 0 ? "Фаянс" : "Фарфор",
                        category.getId(),
                        i % 2 == 0));
            }
        }
    }

    @Override
    public List<ToiletModel> findAllToilets() {
        return toiletRepository.findAllToilets();
    }

    @Override
    public List<ToiletModel> findActiveToilets() {
        return toiletRepository.findActiveToilets();
    }

    @Override
    public ToiletModel findToiletById(int id) {
        return toiletRepository.findToiletById(id);
    }

    @Override
    public ToiletModel addToilet(ToiletModel toilet) {
        return toiletRepository.addToilet(toilet);
    }

    @Override
    public ToiletModel updateToilet(ToiletModel toilet) {
        return toiletRepository.updateToilet(toilet);
    }

    @Override
    public void deleteToilet(int id) {
        toiletRepository.deleteToilet(id);
    }

    @Override
    public boolean softDeleteToilet(int id) {
        return toiletRepository.softDeleteToilet(id);
    }

    @Override
    public void deleteMultipleToilets(List<Integer> ids) {
        toiletRepository.deleteMultipleToilets(ids);
    }

    @Override
    public void softDeleteMultipleToilets(List<Integer> ids) {
        toiletRepository.softDeleteMultipleToilets(ids);
    }

    @Override
    public List<ToiletModel> searchToilets(String searchTerm) {
        return toiletRepository.searchToilets(searchTerm);
    }

    @Override
    public List<ToiletModel> filterToilets(String brand, Double maxPrice, Integer categoryId) {
        return toiletRepository.filterToilets(brand, maxPrice, categoryId);
    }

    @Override
    public List<ToiletModel> getToiletsWithPagination(int page, int size, List<ToiletModel> sourceList) {
        return toiletRepository.findToiletsWithPagination(page, size, sourceList);
    }

    @Override
    public long getTotalToiletsCount(List<ToiletModel> sourceList) {
        return toiletRepository.getTotalCount(sourceList);
    }

    @Override
    public List<String> getAllBrands() {
        return toiletRepository.getAllBrands().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<String> getAllMaterials() {
        return toiletRepository.getAllMaterials().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        return categoryService.findActiveCategories();
    }

    @Override
    public CategoryModel findCategoryById(int id) {
        return categoryService.findCategoryById(id);
    }

    @Override
    public String getCategoryNameById(int categoryId) {
        CategoryModel category = categoryService.findCategoryById(categoryId);
        return category != null ? category.getName() : "Неизвестно";
    }
}