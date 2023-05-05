package com.baloot.model;

import java.util.UUID;

public class Comment {
    private final UUID id = UUID.randomUUID();
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;
    private int like = 0;
    private int dislike = 0;
    public Comment(){};
    public Comment(String userEmail, int commodityId, String text, String date){
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public UUID getId() {
        return id;
    }

    public void addLikeDislike(int vote){
        if(vote == 1)
            like += 1;
        else if (vote == -1)
            dislike += 1;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public Integer getCommodityId() {
        return commodityId;
    }
    public String getText() {
        return text;
    }
    public int getLike() {
        return like;
    }
    public int getDislike() {
        return dislike;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
