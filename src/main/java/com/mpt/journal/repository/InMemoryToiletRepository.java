package com.mpt.journal.repository;

import com.mpt.journal.model.ToiletModel;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryToiletRepository {
    private List<ToiletModel> toilets = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public ToiletModel addToilet(ToiletModel toilet) {
        toilet.setId(idCounter.getAndIncrement());
        toilets.add(toilet);
        return toilet;
    }

    public ToiletModel updateToilet(ToiletModel toilet) {
        return toilets.stream()
                .filter(t -> t.getId() == toilet.getId())
                .findFirst()
                .map(t -> {
                    t.setName(toilet.getName());
                    t.setBrand(toilet.getBrand());
                    t.setModel(toilet.getModel());
                    t.setColor(toilet.getColor());
                    t.setPrice(toilet.getPrice());
                    t.setStockQuantity(toilet.getStockQuantity());
                    t.setMaterial(toilet.getMaterial());
                    t.setCategoryId(toilet.getCategoryId());
                    t.setWaterSaving(toilet.isWaterSaving());
                    return t;
                })
                .orElse(null);
    }

    public void deleteToilet(int id) {
        toilets.removeIf(toilet -> toilet.getId() == id);
    }

    public boolean softDeleteToilet(int id) {
        return toilets.stream()
                .filter(toilet -> toilet.getId() == id && toilet.isActive())
                .findFirst()
                .map(toilet -> {
                    toilet.setActive(false);
                    return true;
                })
                .orElse(false);
    }

    public void deleteMultipleToilets(List<Integer> ids) {
        toilets.removeIf(toilet -> ids.contains(toilet.getId()));
    }

    public void softDeleteMultipleToilets(List<Integer> ids) {
        toilets.stream()
                .filter(toilet -> ids.contains(toilet.getId()) && toilet.isActive())
                .forEach(toilet -> toilet.setActive(false));
    }

    public List<ToiletModel> findAllToilets() {
        return new ArrayList<>(toilets);
    }

    public List<ToiletModel> findActiveToilets() {
        return toilets.stream()
                .filter(ToiletModel::isActive)
                .collect(Collectors.toList());
    }

    public ToiletModel findToiletById(int id) {
        return toilets.stream()
                .filter(toilet -> toilet.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<ToiletModel> searchToilets(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findActiveToilets();
        }

        String term = searchTerm.toLowerCase();
        return toilets.stream()
                .filter(ToiletModel::isActive)
                .filter(toilet ->
                        toilet.getName().toLowerCase().contains(term) ||
                                toilet.getBrand().toLowerCase().contains(term) ||
                                toilet.getModel().toLowerCase().contains(term) ||
                                toilet.getColor().toLowerCase().contains(term) ||
                                toilet.getMaterial().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    public List<ToiletModel> filterToilets(String brand, Double maxPrice, Integer categoryId) {
        List<ToiletModel> activeToilets = findActiveToilets();

        if (brand == null && maxPrice == null && categoryId == null) {
            return activeToilets;
        }

        return activeToilets.stream()
                .filter(toilet -> brand == null || brand.isEmpty() || toilet.getBrand().equalsIgnoreCase(brand))
                .filter(toilet -> maxPrice == null || toilet.getPrice() <= maxPrice)
                .filter(toilet -> categoryId == null || toilet.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    public List<ToiletModel> findToiletsWithPagination(int page, int size, List<ToiletModel> sourceList) {
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

    public long getTotalCount(List<ToiletModel> sourceList) {
        return sourceList.size();
    }

    public Set<String> getAllBrands() {
        return toilets.stream()
                .filter(ToiletModel::isActive)
                .map(ToiletModel::getBrand)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllMaterials() {
        return toilets.stream()
                .filter(ToiletModel::isActive)
                .map(ToiletModel::getMaterial)
                .collect(Collectors.toSet());
    }
}