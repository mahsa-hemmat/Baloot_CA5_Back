package com.baloot.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commodity {
    private int id;
    private String name;
    private int providerId;
    private int price;
    private List<String> categories = new ArrayList<>();
    private double rating;
    private int inStock;
    private String image;
    @JsonIgnore
    private double totalRating = 0;
    @JsonIgnore
    private int ratingCount = 1;
    @JsonIgnore
    Map<UUID,Comment> comments = new HashMap<>();
    public Commodity(){}
    public Commodity(int id, String name, int providerId, int price, List<String> categories,
                     double rating, int inStockCount, String image){
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStockCount;
        this.image = image;
    }
    public Map<UUID, Comment> getComments() {
        return comments;
    }
    public void addComment(Comment comment){
        comments.put(comment.getId(), comment);
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
        return (double) (Math.floor(rating * 100) / 100);
    }
    public void setRating(double rating){
        this.rating = rating;
    }
    public void updateRating(double rating,boolean newRating){
        totalRating+=rating;
        if(newRating)
            ratingCount+=1;
        this.rating = totalRating / ratingCount;
    }
    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }
}
