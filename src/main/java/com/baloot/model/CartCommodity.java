package com.baloot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

public class CartCommodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private List<String> categories = new ArrayList<>();
    private double rating;
    private int inStock;
    private String image;
    private int quantity = 0;

    public CartCommodity(){}
    public CartCommodity(Commodity co){
        id = co.getId();
        name = co.getName();
        providerId = co.getProviderId();
        price = co.getPrice();
        categories = co.getCategories();
        rating = co.getRating();
        inStock = co.getInStock();
        image = co.getImage();
        quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getProviderId() {
        return providerId;
    }
    public int getPrice() {
        return price;
    }
    public void updateInStock(){
        inStock--;
    }
    public int getInStock(){
        return inStock;
    }
    public int getId(){
        return id;
    }
    public String getImage(){ return image; }
    public List<String> getCategories(){
        return categories;
    }
    public double getRating(){
        return rating;
    }
    public void setRating(double rating){
        this.rating = rating;
    }

}
