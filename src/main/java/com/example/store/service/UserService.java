package com.example.store.service;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import com.example.store.validation.LoginValidator;
import com.example.store.validation.RegistrationValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired private UserDao userDao;
    @Autowired private StoreMapper storeMapper;
    @Autowired private RegistrationValidator registrationValidator;
    @Autowired private LoginValidator loginValidator;


    public ResponseEntity<RestResponse> register(RegisterRequestDto requestDto){
        ResponseEntity<RestResponse> responseEntity = null;
        try {
            responseEntity = registrationValidator.check(requestDto);
            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                User user = storeMapper.map(requestDto, User.class);
                userDao.save(user);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    public ResponseEntity<RestResponse> login(User login){
        ResponseEntity<RestResponse> responseEntity = null;
        try {
            responseEntity = loginValidator.check(login);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                responseEntity.getBody().setMessage("Bearer " + Jwts.builder().setSubject(login.getEmail()).claim("roles", "user").setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

}
