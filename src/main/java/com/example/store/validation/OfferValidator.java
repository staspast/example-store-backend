package com.example.store.validation;

import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OfferValidator{

    public ResponseEntity<RestResponse> checkOffer(Offer offer){
        String response = "";
        HttpStatus status = HttpStatus.OK;
        response = addMessageIfNoTitle(offer.getTitle(), response);
        response = addMessageIfNoDescription(offer.getDesc(), response);
        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }

    private String addMessageIfNoTitle(String title, String response){
        if(title == null || title.equals("")){
            response += "Title is empty ";
        }
        return response;
    }

    private String addMessageIfNoDescription(String desc, String response){
        if(desc == null || desc.equals("")){
            response += "Description is empty ";
        }
        return response;
    }
}
