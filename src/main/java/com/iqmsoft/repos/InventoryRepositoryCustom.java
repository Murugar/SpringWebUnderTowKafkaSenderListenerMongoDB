package com.iqmsoft.repos;


public interface InventoryRepositoryCustom {
    int updateStockProduct(String id, int stock);
    int updatePriceProduct(String id, double price);
}
