package com.baloot.model;

import com.baloot.exception.*;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private BuyList buyList = new BuyList();
    private Map<String, Discount> discounts = new HashMap<>();
    private Map<Integer, Commodity> purchasedList = new HashMap<>();
    private Map<Integer, Integer> ratings = new HashMap<>();
    private String lastDiscountUsed = null;

    public User(){}
    public User(String username, String password, String email, String birthDate, String address, int credit){
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public int getCredit() {
        return credit;
    }

    public BuyList getBuyList(){ return buyList; }

    public int getRating(int rating, int id){
        int curr_rating=rating;
        if(ratings.containsKey(id)){
            curr_rating=rating-ratings.get(id);
        }
        ratings.put(id,rating);
        return curr_rating;
    }
    public boolean hasRating(int id){
        return ratings.containsKey(id);
    }

    public void increaseCredit(int amount) {
        credit+=amount;
    }

    public Map<Integer, Commodity> getPurchasedList(){ return purchasedList; }

    public void completePurchase() throws Exception{
        if(credit < buyList.getTotalCost())
            throw new NotEnoughCreditException();
        if(buyList.getCommodities().isEmpty())
            throw new BuyListIsEmptyException();
        for(Commodity commodity:buyList.getCommodities().values()) {
            if (commodity.getInStock() == 0)
                throw new OutOfStockException(commodity.getId());
        }
        credit -= buyList.getTotalCost();
        purchasedList.putAll(buyList.getCommodities());
        for(Commodity commodity:buyList.getCommodities().values())
            commodity.updateInStock();
        buyList = new BuyList();
        if(lastDiscountUsed != null)
            discounts.get(lastDiscountUsed).setAlreadyUsed(true);
    }

    public void addDiscount(Discount discount){
        discounts.put(discount.getDiscountCode(), discount);
    }
    public Boolean isDiscountValid(String discountCode){return discounts.containsKey(discountCode);}

    public void submitDiscount(String discountCode) throws Exception {
        if(!isDiscountValid(discountCode))
            throw new InvalidDiscountException(discountCode);
        else if(isDiscountValid(discountCode) && !hasDiscountExpired(discountCode)) {
            lastDiscountUsed = discountCode;
            buyList.setDiscount(discounts.get(discountCode).getDiscount());
        }
        else if (isDiscountValid(discountCode) && hasDiscountExpired(discountCode))
            throw new DiscountHasExpiredException();
    }
    public Boolean hasDiscountExpired(String discountCode){
        return discounts.get(discountCode).getAlreadyUsed();
    }
}
