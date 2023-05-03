package com.baloot.model;

public class Discount {
    private String discountCode;
    private int discount;
    private Boolean alreadyUsed = false;
    public Discount(){}
    public Discount(String discountCode, int discount){
        this.discountCode = discountCode;
        this.discount = discount;
    }
    public Discount(Discount discount){
        this.discountCode = discount.discountCode;
        this.discount = discount.discount;
        this.alreadyUsed = discount.getAlreadyUsed();
    }

    public int getDiscount() {
        return discount;
    }

    public Boolean getAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(Boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public String getDiscountCode() {
        return discountCode;
    }
}
