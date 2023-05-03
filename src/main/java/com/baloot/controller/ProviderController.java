package com.baloot.controller;

import com.baloot.exception.ProviderNotFoundException;
import com.baloot.model.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/providers")
public class ProviderController {

    @GetMapping("/{provider_id}")
    public ResponseEntity<Object> getProvider(@PathVariable int provider_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            Provider provider = BalootSystem.getInstance().getProvider(provider_id);
            return ResponseEntity.status(HttpStatus.OK).body(provider);

        } catch (ProviderNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{provider_id}/commodities")
    public ResponseEntity<Object> getActor(@PathVariable int provider_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(BalootSystem.getInstance().getProvider(provider_id).getCommodities());
        } catch (ProviderNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
