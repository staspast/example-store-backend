package com.example.store.validation;

import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginValidator extends AbstractValidator {

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseEntity<RestResponse> check(Object o) throws Exception {
        if(!(o instanceof User)){
            throw new Exception("Wrong type exception");
        }
        User user = (User) o;
        String response = "";
        HttpStatus status = HttpStatus.OK;

        response = addMessageIfStringValueIsEmpty(user.getEmail(), response, "Email is empty ");
        response = addMessageIfStringValueIsEmpty(user.getPassword(), response, "Password is empty ");
        response = addMessageIfNoUserIsFoundOrPasswordIsIncorrect(user,response);

        if(!response.equals("")){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new RestResponse(response), status);
    }


    private String addMessageIfNoUserIsFoundOrPasswordIsIncorrect(User user, String response){
        User existingUser = userDao.findByEmail(user.getEmail());
        if(existingUser == null){
            response += "User does not exist ";
        }
        else {
            response = addMessageIfPasswordIsIncorrect(user, existingUser, response);
        }
        return response;
    }

    private String addMessageIfPasswordIsIncorrect(User user, User existingUser, String response){
        return addMessageIfStringsMismatch(user.getPassword(), existingUser.getPassword(), response, "Passwords mismatch ");
    }
}
