package com.baloot.model;

import com.baloot.exception.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyList {
    private Map<Integer, CartCommodity> commodities = new HashMap<>();
    private int discount = 0;
    public BuyList(){}
    public Map<Integer, CartCommodity> getCommodities() {
        return commodities;
    }

    public int getDiscount() {
        return discount;
    }

    public void addCommodity(Commodity commodity) throws InValidInputException {
        if(commodities.containsKey(commodity.getId()))
            throw new InValidInputException("Commodity is Already In The User Buy List.");
        CartCommodity cartCommodity = new CartCommodity(commodity);
        commodities.put(cartCommodity.getId() ,cartCommodity);
    }
    public void removeCommodity(int id) throws CommodityNotFoundException {
        if(!(commodities.containsKey(id)))
            throw new CommodityNotFoundException(id);
        commodities.remove(id);
    }

    public Map<String, Integer> getTotalCost() {
        Map<String, Integer> prices = new HashMap<>();
        int totalPrice = 0;
        for(CartCommodity co:commodities.values()){
            totalPrice += co.getPrice() * co.getQuantity();
        }
        prices.put("originalPrice", totalPrice);
        int discountedPrice = totalPrice * (100 - discount) / 100;
        prices.put("discountedPrice", discountedPrice);
        return prices;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
