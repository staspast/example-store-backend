package com.example.store.validation;

import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginValidator extends AbstractUserValidator{

    @Autowired
    private UserDao userDao;


    public ResponseEntity<RestResponse> checkUser(User user){
        String response = "";
        HttpStatus status = HttpStatus.OK;

        response = addMessageIfUsernameIsEmpty(user.getUsername(), response);
        response = addMessageIfPasswordIsEmpty(user.getPassword(), response);
        response = addMessageIfNoUserIsFoundOrPasswordIsIncorrect(user,response);

        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }


    private String addMessageIfNoUserIsFoundOrPasswordIsIncorrect(User user, String response){
        User existingUser = userDao.findByUsername(user.getUsername());
        if(existingUser == null){
            response += "User does not exist ";
        }
        else {
            response = addMessageIfPasswordIsIncorrect(user, existingUser, response);
        }
        return response;
    }

    private String addMessageIfPasswordIsIncorrect(User user, User existingUser, String response){
        response = addMessageIfPasswordsMismatch(user.getPassword(), existingUser.getPassword(), response);
        return response;
    }
}
