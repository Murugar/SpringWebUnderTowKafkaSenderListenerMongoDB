package com.iqmsoft.kafka.producer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqmsoft.model.Inventory;
import com.iqmsoft.repos.InventoryRepository;
import java.util.HashMap;


@Component
public class KafkaProducer implements Producer {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	Sender template;

	@Override
	public void updateDataStockAndPublishToKafka(String id, int qty) {

		Inventory inventory = repository.findById(id);

		// send to kafka
		if (qty > inventory.getStock()) {

			Map<String, String> value = new HashMap<String, String>() {
				{
					put("message", Integer.toString(qty));
				}
			};
			template.sendMessage("test", value.toString());
			
			
		}
		repository.updateStockProduct(id, qty);
	}

	@Override
	public void DeleteInventoryPublishToKafka(String id) {

		Map<String, String> value = new HashMap<String, String>() {
			{
				put("message", id);
			}
		};
		template.sendMessage("test", value.toString());
		
		

	}

	@Override
	public void CreateInventoryPublishToKafka(Inventory p) {

		
		 Map<String, String> value = new HashMap<String, String>() {{
             put("message", p.toString());
         }};
       
         template.sendMessage("test", value.toString());
         

	}

	@Override
	public void InventoryPublishToKafka(List<Inventory> p) {
		
		 Map<String, String> value = new HashMap<String, String>() {{
             put("message", p.toString());
         }};
         template.sendMessage("test", value.toString());
        
		
		
		
	}

	@Override
	public void updateDataPriceAndPublishToKafka(String id, double price) {

		Inventory inventory = repository.findById(id);
	
		if (price < inventory.getPrice()) {
			
			 Map<String, String> value = new HashMap<String, String>() {{
	             put("message",  Double.toString(price));
	         }};
	         template.sendMessage("test", value.toString());
	       
			
		}
		repository.updatePriceProduct(id, price);
	}
}
