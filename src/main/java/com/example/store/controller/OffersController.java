package com.example.store.controller;

import com.example.store.dto.OfferSearchDto;
import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import com.example.store.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secure/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping("/get")
    public List<Offer> getOffers(){
        return offersService.getAll();
    }

    @PostMapping("/search")
    public List<Offer> searchOffers(@RequestBody OfferSearchDto searchDto){
        return offersService.searchOffers(searchDto);
    }

    @PostMapping("/save")
    public ResponseEntity<RestResponse> saveOffer(@RequestBody Offer offer){
        return offersService.saveOffer(offer);
    }
}
