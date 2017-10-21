package com.example.store.service;

import com.example.store.model.dao.OffersDao;
import com.example.store.model.entity.Offer;
import com.example.store.rest.RestResponse;
import com.example.store.validation.OfferValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OffersServiceTest {

    @Mock
    private OffersDao offersDao;

    @Mock
    private OfferValidator offerValidator;

    @Spy @InjectMocks
    private OffersService offersService;

    private Offer offer;

    @Before
    public void setUp(){
        offer = Offer.builder().title("offer").description("desc").build();
    }

    @Test
    public void shouldReturnAllOffers(){
        when(offersDao.findAll()).thenReturn(new ArrayList<>());
        offersService.getAll();

        verify(offersDao).findAll();
    }

    @Test
    public void shouldSaveValidOffer() throws Exception {
        when(offerValidator.check(offer)).thenReturn(createValidResponse());

        offersService.saveOffer(offer);

        verify(offersDao).save(offer);
    }

    @Test
    public void shouldNotSaveInvalidOffer() throws Exception {
        when(offerValidator.check(offer)).thenReturn(createInvalidResponse());

        offersService.saveOffer(offer);

        verify(offersDao, never()).save(offer);
    }



    private ResponseEntity<RestResponse> createValidResponse(){
        return new ResponseEntity<>(new RestResponse(""), HttpStatus.OK);
    }

    private ResponseEntity<RestResponse> createInvalidResponse(){
        return new ResponseEntity<>(new RestResponse("message"), HttpStatus.BAD_REQUEST);
    }
}