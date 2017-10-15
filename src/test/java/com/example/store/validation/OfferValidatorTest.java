package com.example.store.validation;

import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OfferValidatorTest {

    @Spy
    private OfferValidator offerValidator;

    @Test
    public void shouldValidateValidOffer(){
        Offer offer = createValidOffer();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getMessage(), "");
    }

    @Test
    public void shouldInvalidateInvalidOfferWithEmptyTitle(){
        Offer offer = createInvalidOfferWithEmptyTitle();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Title is empty ");
    }

    @Test
    public void shouldInvalidateInvalidOfferWithNullTitle(){
        Offer offer = createInvalidOfferWithNullTitle();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Title is empty ");
    }

    @Test
    public void shouldInvalidateInvalidOfferWithEmptyDescription(){
        Offer offer = createInvalidOfferWithEmptyDesc();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Description is empty ");
    }

    @Test
    public void shouldInvalidateInvalidOfferWithNullDescription(){
        Offer offer = createInvalidOfferWithNullDesc();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Description is empty ");
    }

    @Test
    public void shouldInvalidateInvalidOfferWithNullTitleAndDescription(){
        Offer offer = createInvalidOfferWithNullDescAndTitle();
        ResponseEntity<RestResponse> responseEntity = offerValidator.checkOffer(offer);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Title is empty Description is empty ");
    }




    //Private create test object methods

    private Offer createValidOffer(){
        return Offer.builder().title("title").desc("description").build();
    }

    private Offer createInvalidOfferWithEmptyTitle(){
        return Offer.builder().title("").desc("description").build();
    }

    private Offer createInvalidOfferWithNullTitle(){
        return Offer.builder().title("").desc("description").build();
    }

    private Offer createInvalidOfferWithEmptyDesc(){
        return Offer.builder().title("title").desc("").build();
    }

    private Offer createInvalidOfferWithNullDesc(){
        return Offer.builder().title("title").desc(null).build();
    }

    private Offer createInvalidOfferWithNullDescAndTitle(){
        return Offer.builder().title(null).desc(null).build();
    }
}