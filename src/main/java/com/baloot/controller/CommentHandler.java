package com.baloot.controller;

import com.baloot.exception.CommodityNotFoundException;
import com.baloot.model.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentHandler {

    @PutMapping("/{commodity_id}")
    public ResponseEntity<Object> addComment(@PathVariable Integer commodity_id, @RequestBody String comment) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            BalootSystem.getInstance().getDataBase().addComment(comment, commodity_id);
            return ResponseEntity.status(HttpStatus.OK).body("Comment added successfully.");
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
