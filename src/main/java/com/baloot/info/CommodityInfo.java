package com.baloot.info;

import com.baloot.model.Commodity;

import java.util.List;

public class CommodityInfo {
    private final int id;
    private final String name;
    private final int providerId;
    private final int price;
    private final List<String> categories;
    private final double rating;
    private final int inStock;
    private final String image;

    public CommodityInfo(Commodity commodity){
        id = commodity.getId();
        name = commodity.getName();
        providerId = commodity.getProviderId();
        price = commodity.getPrice();
        categories = commodity.getCategories();
        rating = commodity.getRating();
        inStock = commodity.getInStock();
        image = commodity.getImage();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProviderId() {
        return providerId;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public double getRating() {
        return rating;
    }

    public int getInStock() {
        return inStock;
    }

    public String getImage() {
        return image;
    }
}
