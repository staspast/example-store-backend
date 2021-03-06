package com.example.store.service;

import com.example.store.dto.OfferSearchDto;
import com.example.store.model.dao.OffersDao;
import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import com.example.store.validation.OfferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffersService {

    @Autowired
    private OffersDao offersDao;

    @Autowired
    private OfferValidator offerValidator;

    public List<Offer> getAll(){
        List<Offer> list = new ArrayList<>();
        offersDao.findAll().forEach(list::add);
        return list;
    }

    public List<Offer> searchOffers(OfferSearchDto searchDto){
        return offersDao.search(searchDto.getTitle(), searchDto.getDateFrom(), searchDto.getDateTo());
    }

    public ResponseEntity<RestResponse> saveOffer(Offer offer){
        ResponseEntity<RestResponse> response = null;
        try {
            response = offerValidator.check(offer);
            if(response.getStatusCode() == HttpStatus.OK){
                offersDao.save(offer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
