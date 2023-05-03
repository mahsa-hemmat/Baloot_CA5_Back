package com.baloot.model;

import java.util.ArrayList;
import java.util.List;

public class Provider {
    private int id;
    private String name;
    private String registryDate;
    private List<Commodity> commodities = new ArrayList<>();
    private double rating = 0;
    public Provider(){}
    public Provider(int id, String name, String registryDate){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public int getId() {
        return id;
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

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public void addNewCommodity(Commodity newCommodity){
        commodities.add(newCommodity);
        calRating();
        //rating = ((rating*commodities.size()-1) + newCommodity.getRating()) / commodities.size();
    }

    public void calRating(){
        double sum = 0;
        for(int i = 0 ; i < commodities.size(); i++) {
            sum += commodities.get(i).getRating();
        }
        rating = sum/commodities.size();
    }

    public double getRating(){
        return (double) (Math.floor(rating * 100) / 100);
    }
}
