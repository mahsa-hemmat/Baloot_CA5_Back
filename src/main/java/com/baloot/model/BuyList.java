package com.baloot.model;

import com.baloot.exception.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyList {
    private Map<Integer, Commodity> commodities = new HashMap<>();
    int discount = 0;
    public BuyList(){}
    public Map<Integer, Commodity> getCommodities() {
        return commodities;
    }
    public void addCommodity(Commodity commodity) throws InValidInputException {
        if(commodities.containsKey(commodity.getId()))
            throw new InValidInputException("Commodity is Already In The User Buy List.");
        commodities.put(commodity.getId() ,commodity);
    }
    public void removeCommodity(int id) throws CommodityNotFoundException {
        if(!(commodities.containsKey(id)))
            throw new CommodityNotFoundException(id);
        commodities.remove(id);
    }

    public List<Commodity> printList() {
        return new ArrayList<Commodity>(commodities.values());
    }

    public int getTotalCost() {
        int totalPrice = 0;
        for(Commodity co:commodities.values()){
            totalPrice += co.getPrice();
        }
        totalPrice -= (discount * totalPrice) / 100;
        return totalPrice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
