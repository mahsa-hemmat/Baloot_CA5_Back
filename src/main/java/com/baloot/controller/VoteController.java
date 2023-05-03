package com.baloot.controller;

import com.baloot.exception.CommodityNotFoundException;
import com.baloot.exception.ScoreOutOfBoundsException;
import com.baloot.exception.UserNotFoundException;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vote")
public class VoteController {
    @PostMapping("/{commodity_id}/{vote}")
    public ResponseEntity<Object> vote(@PathVariable Integer commodity_id, @PathVariable Integer vote) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            BalootSystem.getInstance().getDataBase().rateCommodity(commodity_id, vote);
            return ResponseEntity.status(HttpStatus.OK).body("Comment added successfully.");
        } catch (CommodityNotFoundException | UserNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ScoreOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
