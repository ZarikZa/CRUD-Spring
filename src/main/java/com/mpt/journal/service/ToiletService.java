package com.mpt.journal.service;

import com.mpt.journal.model.ToiletModel;

import java.util.List;

import com.mpt.journal.model.CategoryModel;


public interface ToiletService {
    List<ToiletModel> findAllToilets();
    List<ToiletModel> findActiveToilets();
    ToiletModel findToiletById(int id);
    ToiletModel addToilet(ToiletModel toilet);
    ToiletModel updateToilet(ToiletModel toilet);
    void deleteToilet(int id);
    boolean softDeleteToilet(int id);
    void deleteMultipleToilets(List<Integer> ids);
    void softDeleteMultipleToilets(List<Integer> ids);
    List<ToiletModel> searchToilets(String searchTerm);
    List<ToiletModel> filterToilets(String brand, Double maxPrice, String categoryName);
    List<ToiletModel> getToiletsWithPagination(int page, int size, List<ToiletModel> sourceList);
    long getTotalToiletsCount(List<ToiletModel> sourceList);
    List<String> getAllBrands();
    List<String> getAllMaterials();
    List<String> getAllCategoryNames();
    List<CategoryModel> getAllCategories();
    CategoryModel findCategoryById(int id);
}