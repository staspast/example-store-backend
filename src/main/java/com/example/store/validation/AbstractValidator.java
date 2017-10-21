package com.example.store.validation;

import com.example.store.rest.RestResponse;
import org.springframework.http.ResponseEntity;

public abstract class AbstractValidator {

    protected String addMessageIfStringValueIsEmpty(String value, String response, String message){
        if(value == null || value.equals("")){
            response += message;
        }
        return response;
    }

    protected String addMessageIfStringsMismatch(String value1, String value2, String response, String message){
        if(!value1.equals(value2)){
            response += message;
        }
        return response;
    }

    public abstract ResponseEntity<RestResponse> check(Object o) throws Exception;
}
