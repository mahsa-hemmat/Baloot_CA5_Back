package com.baloot.info;

public class CommentInfo {
    private int commodityId;
    private String text;

    public CommentInfo() {
        text = null;
        commodityId = 0;
    }

    public CommentInfo(int commodityId, String text) {
        this.text = text;
        this.commodityId = commodityId;
    }

    public int getCommodityId() {return commodityId;}
    public String getText() {return text;}

    public void setCommodityId(int commodityId) { this.commodityId = commodityId; }
    public void setText(String text) { this.text = text; }
}
