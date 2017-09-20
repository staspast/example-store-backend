package com.example.store.validation;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired private UserDao userDao;

    public ResponseEntity<RestResponse> checkUser(User user){
        return new ResponseEntity<>(new RestResponse(""), HttpStatus.OK);
    }

    public boolean userAlreadyExists(String username){
        return userDao.findByUsername(username) != null;
    }

    public boolean passwordsMatch(RegisterRequestDto requestDto){
        return requestDto.getPassword().equals(requestDto.getPasswordRepeat());
    }
}
