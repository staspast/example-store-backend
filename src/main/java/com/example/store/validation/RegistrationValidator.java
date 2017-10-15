package com.example.store.validation;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.model.dao.UserDao;
import com.example.store.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RegistrationValidator extends AbstractUserValidator {

    @Autowired private UserDao userDao;

    public ResponseEntity<RestResponse> checkUser(RegisterRequestDto user){
        String response = "";
        HttpStatus status = HttpStatus.OK;
        response = addMessageIfUsernameIsEmpty(user.getUsername(), response);
        response = addMessageIfPasswordIsEmpty(user.getPassword(), response);
        response = addMessageIfUserAlreadyExists(user.getUsername(), response);
        response = addMessageIfPasswordsMismatch(user.getPassword(), user.getPasswordRepeat(), response);
        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }

    private String addMessageIfUserAlreadyExists(String username, String response){
        if(userDao.findByUsername(username) != null){
            response += "User already exists ";
        }
        return response;
    }
}
