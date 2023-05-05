package com.baloot.controller;

import com.baloot.exception.ProviderNotFoundException;
import com.baloot.info.AbstractCommodityInfo;
import com.baloot.info.ProviderInfo;
import com.baloot.model.*;
import com.baloot.service.BalootSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/providers")
public class ProviderController {

    @GetMapping("/{provider_id}")
    public ResponseEntity<Object> getProvider(@PathVariable int provider_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            ProviderInfo providerInfo = new ProviderInfo(BalootSystem.getInstance().getProvider(provider_id));
            return ResponseEntity.status(HttpStatus.OK).body(providerInfo);

        } catch (ProviderNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{provider_id}/commodities")
    public ResponseEntity<Object> getProviderCommodities(@PathVariable int provider_id) {
        if (!BalootSystem.getInstance().hasAnyUserLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in. Please login first");
        try {
            ArrayList<AbstractCommodityInfo> abstractCommodityInfos = new ArrayList<>();
            for(Commodity commodity : BalootSystem.getInstance().getProvider(provider_id).getCommodities()){
                abstractCommodityInfos.add(new AbstractCommodityInfo(commodity));
            }
            return ResponseEntity.status(HttpStatus.OK).body(abstractCommodityInfos);
        } catch (ProviderNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
