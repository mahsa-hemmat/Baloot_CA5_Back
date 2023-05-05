package com.baloot.controller;

import com.baloot.exception.CommodityNotFoundException;
import com.baloot.exception.ScoreOutOfBoundsException;
import com.baloot.exception.UserNotFoundException;
import com.baloot.info.AbstractCommodityInfo;
import com.baloot.info.CommentInfo;
import com.baloot.info.CommodityInfo;
import com.baloot.model.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/commodities")
public class CommodityHandler {

    @GetMapping("")
    public ResponseEntity<Object> getCommodities(@RequestParam(value = "searchType", required=false) Integer searchType,
                                                 @RequestParam(value = "keyword", required=false) String keyword,
                                                 @RequestParam(value = "sortType", required=false) String sort) {
        BalootSystem balootSystem = BalootSystem.getInstance();
        System.out.println(searchType+ " "+ keyword + " "+ sort);
        if (!balootSystem.hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first.");
        boolean emptySort = sort == null || sort.isBlank() || sort.isEmpty();
        boolean emptyKeyword = keyword == null || keyword.isBlank() || keyword.isEmpty();
        if (!emptySort && (!sort.equals("name")) && (!sort.equals("price"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid sort parameter.");
        }
        if ((searchType != null && emptyKeyword) || (searchType == null && !emptyKeyword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("keyword and searchType should be provided together.");

        }
        if (searchType != null && searchType != 1 && searchType != 2 && searchType != 3) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid search type.");

        }
        ArrayList<AbstractCommodityInfo> abstractCommodityInfos = new ArrayList<>();
        List<Commodity> commodityList = new ArrayList<>(balootSystem.getDataBase().getCommodities().getCommodities().values());
        if(emptyKeyword)
            balootSystem.getDataBase().getCommodities().clearFilter();
        if (!emptyKeyword) {
            if(searchType == 1)
                commodityList = balootSystem.getDataBase().getCommodities().filterByName(keyword);
            if(searchType == 2)
                commodityList = balootSystem.getDataBase().getCommodities().filterByCategory(keyword);
            if(searchType == 3)
                commodityList = balootSystem.getDataBase().getCommodities().filterByProviderName(keyword);
        }
        if (!emptySort)
            commodityList = sort.equals("name") ? balootSystem.getDataBase().getCommodities().sortByName(commodityList) :
                    balootSystem.getDataBase().getCommodities().sortByPrice(commodityList) ;

        for(Commodity commodity : commodityList){
            abstractCommodityInfos.add(new AbstractCommodityInfo(commodity));
        }
        return ResponseEntity.status(HttpStatus.OK).body(abstractCommodityInfos);
    }

    @GetMapping("/{commodity_id}")
    public ResponseEntity<Object> getCommodity(@PathVariable int commodity_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            CommodityInfo commodityInfo = new CommodityInfo(BalootSystem.getInstance().getDataBase().getCommodityById(commodity_id));
            return ResponseEntity.status(HttpStatus.OK).body(commodityInfo);
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{commodity_id}/comments")
    public ResponseEntity<Object> getCommodityComments(@PathVariable int commodity_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            List<Comment> comments = new ArrayList<>(BalootSystem.getInstance().getDataBase().getCommodityById(commodity_id).getComments().values());
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{commodity_id}/recommended")
    public ResponseEntity<Object> getRecommendedCommodities(@PathVariable int commodity_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            List<Commodity> recommended = BalootSystem.getInstance().getDataBase().recommenderSystem(commodity_id);
            ArrayList<AbstractCommodityInfo> abstractCommodityInfos = new ArrayList<>();
            for(Commodity commodity : recommended){
                abstractCommodityInfos.add(new AbstractCommodityInfo(commodity));
            }
            return ResponseEntity.status(HttpStatus.OK).body(abstractCommodityInfos);
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/{commodity_id}")
    public ResponseEntity<Object> rateCommodity(@PathVariable Integer commodity_id, @RequestParam(value = "score") int score) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            BalootSystem.getInstance().getDataBase().rateCommodity(commodity_id, score);
            return ResponseEntity.status(HttpStatus.OK).body("score added successfully.");
        } catch (CommodityNotFoundException | UserNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ScoreOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
