package com.iqmsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.iqmsoft.kafka.producer.Producer;
import com.iqmsoft.model.Inventory;
import com.iqmsoft.repos.InventoryRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    ConfigurableApplicationContext context;

    @RequestMapping(value = "/updateStock", method = RequestMethod.POST)
    public ModelAndView updateStockInventory(@ModelAttribute("id") String id, @ModelAttribute("stock") int stock){
        
        Producer producer = context.
        		getBean("kafkaProducer",Producer.class);

        producer.updateDataStockAndPublishToKafka(id, stock);
        
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "editStock/{id}", method = RequestMethod.GET)
    public String updateStockInventory(Model model, @PathVariable String id){
        Inventory inventory = repository.findById(id);

        model.addAttribute(inventory);

        return "updateStock";
    }

    @RequestMapping(value = "editPrice/{id}", method = RequestMethod.GET)
    public String updatePriceInventory(Model model, @PathVariable String id){
        Inventory inventory = repository.findById(id);

        model.addAttribute(inventory);

        return "updatePrice";
    }

    @RequestMapping(value = "/updatePrice", method = RequestMethod.POST)
    public ModelAndView updatePriceInventory(@ModelAttribute("id") String id, @ModelAttribute("price") double newPrice){
        Producer producer = context.getBean("kafkaProducer",Producer.class);

        producer.updateDataPriceAndPublishToKafka(id, newPrice);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addInventory(@ModelAttribute("inventory") Inventory inventory, Model model){
        repository.save(inventory);
        
        Producer producer = context.getBean("kafkaProducer",Producer.class);
        producer.CreateInventoryPublishToKafka(inventory);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView addInventory(HttpSession httpSession){
        return new ModelAndView("create");
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable String id){
        repository.delete(id);
        
        Producer producer = context.getBean("kafkaProducer",Producer.class);
        producer.DeleteInventoryPublishToKafka(id);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/")
    public ModelAndView getAll(HttpSession httpSession){
        List<Inventory> result = repository.findAll();
        
        
        Producer producer = context.getBean("kafkaProducer",Producer.class);
        producer.InventoryPublishToKafka(result);
        
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("inventories", result);

        return modelAndView;
    }
}
