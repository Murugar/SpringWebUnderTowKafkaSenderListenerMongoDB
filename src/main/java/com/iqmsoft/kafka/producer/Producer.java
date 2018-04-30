package com.iqmsoft.kafka.producer;

import java.util.List;

import com.iqmsoft.model.Inventory;

public interface Producer {

	void InventoryPublishToKafka(List<Inventory> p);
	void CreateInventoryPublishToKafka(Inventory p);
	void DeleteInventoryPublishToKafka(String id);
    void updateDataPriceAndPublishToKafka(String id, double price);
    void updateDataStockAndPublishToKafka(String id, int qty);
}
