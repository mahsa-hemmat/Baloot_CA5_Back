package com.baloot.controller;

import com.baloot.exception.CommodityNotFoundException;
import com.baloot.exception.InValidInputException;
import com.baloot.exception.OutOfStockException;
import com.baloot.exception.UserNotFoundException;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baloot.model.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping("")
    public ResponseEntity<Object> getUser() {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/buylist")
    public ResponseEntity<Object> getBuyList() {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            List<Commodity> buyList = user.getBuyList().getCommodities().values().stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(buyList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/buyList")
    public ResponseEntity<Object> addToBuyList(@RequestBody int commodityId) {
        try {
            BalootSystem.getInstance().getLoggedInUser();
            BalootSystem.getInstance().getDataBase().addToBuyList(commodityId);
            return ResponseEntity.status(HttpStatus.OK).body("Commodity is added to buylist successfully.");
        } catch (OutOfStockException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (CommodityNotFoundException | UserNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @DeleteMapping("/buyList/{commodityId}")
    public ResponseEntity<Object> removeFromBuyList(@PathVariable int commodityId) {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            user.getBuyList().removeCommodity(commodityId);
            return ResponseEntity.status(HttpStatus.OK).body("Commodity is removed from buylist successfully.");
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getHistory() {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            return ResponseEntity.status(HttpStatus.CREATED).body(user.getPurchasedList().values());
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<Object> addCredit(@RequestBody int credit) {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            if (credit < 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed. Credit Cannot Be Negative");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Credit added successfully.");
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/payments")
    //public ResponseEntity<Object> makePayment(@RequestBody PaymentRequest request) {
    public ResponseEntity<Object> makePayment() {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            // add code here
            return ResponseEntity.status(HttpStatus.OK).body("Credit added successfully.");
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
