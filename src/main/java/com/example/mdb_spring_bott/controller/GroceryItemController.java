package com.example.mdb_spring_bott.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mdb_spring_bott.model.GroceryItem;
import com.example.mdb_spring_bott.repository.GroceryItemRepository;

@CrossOrigin(origins = "http://localhost:63343")
@RestController
@RequestMapping("/api")
public class GroceryItemController {

    @Autowired
    GroceryItemRepository groceryItemRepository;

    @GetMapping("/grocery")
    public ResponseEntity<List<GroceryItem>> getAll(@RequestParam(required = false) String name) {
        try {
            List<GroceryItem> items = new ArrayList<GroceryItem>();

            if (name == null)
                items.addAll(groceryItemRepository.findAll());
            else
                items.addAll(groceryItemRepository.findByName(name));

            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grocery/category/{category}")
    public ResponseEntity<List<GroceryItem>> getAllCategory(@PathVariable("category") String category) {
        try {
            List<GroceryItem> items = groceryItemRepository.findByCategory(category);

            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/grocery/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        try {
            List<GroceryItem> items = groceryItemRepository.findAllCategories();
            List<String> categories = items.stream()
                    .map(GroceryItem::getCategory)
                    .distinct()
                    .collect(Collectors.toList());

            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/grocery/quantity")
    public ResponseEntity<Integer> getTotalQuantity() {
        try {
            List<GroceryItem> items = groceryItemRepository.findAllQuantity();
            int totalQuantity = items.stream()
                    .map(GroceryItem::getQuantity)
                    .reduce(0, Integer::sum);

            if (totalQuantity == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(totalQuantity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grocery/{id}")
    public ResponseEntity<Optional<GroceryItem>> getAllId(@PathVariable("id") String id) {
        try {
            Optional<GroceryItem> items = groceryItemRepository.findById(id);

            if (!items.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/grocery")
    public ResponseEntity<GroceryItem> create(@RequestBody GroceryItem item) {
        try {
            GroceryItem _item = groceryItemRepository.save(new GroceryItem(item.getName(), item.getQuantity(), item.getCategory()));
            return new ResponseEntity<>(_item, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/grocery/{id}")
    public ResponseEntity<GroceryItem> update(@PathVariable("id") String id, @RequestBody GroceryItem item) {
        Optional<GroceryItem> itemData = groceryItemRepository.findById(id);

        if (itemData.isPresent()) {
            GroceryItem _item = itemData.get();
            _item.setName(item.getName());
            _item.setQuantity(item.getQuantity());
            _item.setCategory(item.getCategory());
            return new ResponseEntity<>(groceryItemRepository.save(_item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<GroceryItem> delete(@PathVariable("id") String id) {
        try {
            groceryItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/grocery/category/{category}")
    public ResponseEntity<GroceryItem> deleteCategory(@PathVariable("category") String category) {
        try {
            groceryItemRepository.deleteByCategory(category);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
