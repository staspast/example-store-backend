package com.example.store.validation;

import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import org.hibernate.WrongClassException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OfferValidator extends AbstractValidator {

    @Override
    public ResponseEntity<RestResponse> check(Object o) throws Exception {
        if(!(o instanceof Offer)){
            throw new Exception("Wrong type exception");
        }
        Offer offer = (Offer) o;
        String response = "";
        HttpStatus status = HttpStatus.OK;
        response = addMessageIfStringValueIsEmpty(offer.getTitle(), response, "Title is empty ");
        response = addMessageIfStringValueIsEmpty(offer.getDesc(), response, "Description is empty ");
        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }
}
