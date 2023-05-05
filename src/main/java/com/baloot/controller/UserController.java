package com.baloot.controller;

import com.baloot.exception.*;
import com.baloot.info.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baloot.model.*;

import java.util.*;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping("")
    public ResponseEntity<Object> getUser() {
        try {
            UserInfo userInfo = new UserInfo(BalootSystem.getInstance().getLoggedInUser());
            return ResponseEntity.status(HttpStatus.OK).body(userInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/buylist")
    public ResponseEntity<Object> getBuyList() {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            return ResponseEntity.status(HttpStatus.OK).body(user.getBuyList().getCommodities().values());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/buylist")
    public ResponseEntity<Object> addToBuyList(@RequestParam(value = "commodityId") int commodityId) {
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

    @DeleteMapping("/buylist")
    public ResponseEntity<Object> removeFromBuyList(@RequestParam(value = "commodityId") int commodityId) {
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
    public ResponseEntity<Object> addCredit(@RequestParam(value = "credit") int credit) {
        try {
            BalootSystem.getInstance().getLoggedInUser();
            if (credit < 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed. Credit Cannot Be Negative");
            }
            BalootSystem.getInstance().increaseCredit(credit);
            return ResponseEntity.status(HttpStatus.OK).body("Credit added successfully.");
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @GetMapping ("/payment")
    public ResponseEntity<Object> getPaymentData(@RequestBody List<CartCommodityRequest> cartCommodity) {
        try {
            User user = BalootSystem.getInstance().getLoggedInUser();
            BalootSystem baloot = BalootSystem.getInstance();
            for (CartCommodityRequest cc : cartCommodity) {
                user.getBuyList().getCommodities().get(cc.getCommodityId()).setQuantity(cc.getQuantity());
            }
            Map<String, Integer> prices = baloot.getLoggedInUser().getBuyList().getTotalCost();
            return ResponseEntity.status(HttpStatus.OK).body(prices.get("originalPrice"));
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Object> makePayment() {
        try {
            BalootSystem.getInstance().getLoggedInUser();
            BalootSystem.getInstance().getDataBase().purchase();
            return ResponseEntity.status(HttpStatus.OK).body("payment done successfully.");
        } catch (InValidInputException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (OutOfStockException| BuyListIsEmptyException | NotEnoughCreditException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (CommodityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/discount")
    public ResponseEntity<Object> applyDiscountCode(@RequestParam(value = "discountcode", required = false) String discount) {
        try {
            BalootSystem baloot = BalootSystem.getInstance();
            baloot.getLoggedInUser().submitDiscount(discount);
            Map<String, Integer> prices = baloot.getLoggedInUser().getBuyList().getTotalCost();
            return ResponseEntity.status(HttpStatus.OK).body(prices.get("discountedPrice"));
        } catch (InValidInputException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (InvalidDiscountException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DiscountHasExpiredException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


}
