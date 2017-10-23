package com.example.store.validation;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.model.dao.UserDao;
import com.example.store.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RegistrationValidator extends AbstractValidator {

    @Autowired private UserDao userDao;

    @Override
    public ResponseEntity<RestResponse> check(Object o) throws Exception {
        if(!(o instanceof RegisterRequestDto)){
            throw new Exception("Wrong type exception");
        }
        RegisterRequestDto user = (RegisterRequestDto) o;

        String response = "";
        HttpStatus status = HttpStatus.OK;

        response = addMessageIfStringValueIsEmpty(user.getPassword(), response, "Password is empty ");
        response = addMessageIfStringValueIsEmpty(user.getFirstName(), response, "First name is empty ");
        response = addMessageIfStringValueIsEmpty(user.getLastName(), response, "Last name is empty");
        response = addMessageIfStringValueIsEmpty(user.getEmail(), response, "Email is empty ");
        response = addMessageIfUserAlreadyExists(user.getEmail(), response);
        response = addMessageIfStringsMismatch(user.getPassword(), user.getPasswordRepeat(), response, "Passwords mismatch ");

        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }

    private String addMessageIfUserAlreadyExists(String email, String response){
        if(userDao.findByEmail(email) != null){
            response += "User already exists ";
        }
        return response;
    }
}
