package com.baloot.controller;

import com.baloot.exception.CommodityNotFoundException;
import com.baloot.exception.ProviderNotFoundException;
import com.baloot.model.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/commodity")
public class CommodityHandler {

    @GetMapping("/{commodity_id}")
    public ResponseEntity<Object> getProvider(@PathVariable int commodity_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(BalootSystem.getInstance().getDataBase().getCommodityById(commodity_id));
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{commodity_id}/recommended")
    public ResponseEntity<Object> getRecommendedCommodities(@PathVariable int commodity_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            List<Commodity> recommended = BalootSystem.getInstance().getDataBase().recommenderSystem(commodity_id);
            return ResponseEntity.status(HttpStatus.OK).body(recommended);
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
