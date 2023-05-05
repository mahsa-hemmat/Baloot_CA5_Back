package com.baloot.controller;

import com.baloot.exception.CommentNotFoundException;
import com.baloot.exception.CommodityNotFoundException;
import com.baloot.info.CommentInfo;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentHandler {

    @PostMapping ("")
    public ResponseEntity<Object> addComment(@RequestBody CommentInfo commentInfo) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            BalootSystem.getInstance().getDataBase().addComment(commentInfo.getText(), commentInfo.getCommodityId());
            return ResponseEntity.status(HttpStatus.OK).body("Comment added successfully.");
        } catch (CommodityNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<Object> voteComment(@PathVariable(value = "commentId") String  commentId,
                                              @RequestParam(value = "vote") int vote) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            BalootSystem.getInstance().getDataBase().voteComment(commentId, vote);
            return ResponseEntity.status(HttpStatus.OK).body("vote added successfully.");
        } catch (CommentNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
