package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.service.RestaurantService;

 @Controller
public class HomeController {
	 private final RestaurantService restaurantService;

    public HomeController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
	    
     @GetMapping("/")
     public String index(Model model) {
    	 List<Restaurant> newRestaurants = restaurantService.findTop8RestaurantsByOrderByCreatedAtDesc();
         List<Restaurant> popularRestaurants = restaurantService.findTop3RestaurantsByOrderByReservationCountDesc();
         
         model.addAttribute("newRestaurants", newRestaurants);
         model.addAttribute("popularRestaurants", popularRestaurants);
         
         return "index";
     }   
}

