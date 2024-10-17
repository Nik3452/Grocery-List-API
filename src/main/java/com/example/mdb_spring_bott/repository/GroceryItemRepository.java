package com.example.mdb_spring_bott.repository;

import java.util.List;

import com.example.mdb_spring_bott.model.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {
    List<GroceryItem> findByName(String name);

    List<GroceryItem> findByCategory(String category);

    @Query(value = "{}", fields = "{ 'category' : 1 }")
    List<GroceryItem> findAllCategories();

    @Query(value = "{}", fields = "{ 'quantity' : 1 }")
    List<GroceryItem> findAllQuantity();

    void deleteByCategory(String category);
}