package com.iqmsoft.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iqmsoft.model.Inventory;

import java.util.List;


public interface InventoryRepository extends MongoRepository<Inventory, String>, InventoryRepositoryCustom {
    List<Inventory> findAll();
    Inventory findById(String id);
}
