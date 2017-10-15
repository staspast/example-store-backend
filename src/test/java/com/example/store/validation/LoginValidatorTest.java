package com.example.store.validation;

import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {

    @Mock
    private UserDao userDao;

    @Spy @InjectMocks
    private LoginValidator loginValidator;

    @Test
    public void shouldValidateValidUser(){
        User user = createValidUser();
        when(userDao.findByUsername("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.checkUser(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getMessage(), "");
    }

    @Test
    public void shouldInvalidateInvalidUserWithEmptyUsername(){
        User user = createInvalidUserWithEmptyUsername();
        ResponseEntity<RestResponse> responseEntity = loginValidator.checkUser(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Username is empty User does not exist ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithNullUsername(){
        User user = createInvalidUserWithNullUsername();
        ResponseEntity<RestResponse> responseEntity = loginValidator.checkUser(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Username is empty User does not exist ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithEmptyPassword(){
        User user = createInvalidUserWithEmptyPassword();
        when(userDao.findByUsername("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.checkUser(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Password is empty Passwords mismatch ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithIncorrectPassword(){
        User user = createInvalidUserWithIncorrectPassword();
        when(userDao.findByUsername("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.checkUser(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Passwords mismatch ");
    }


    private User createValidUser(){
        return User.builder().username("user").password("qwer").build();
    }

    private User createInvalidUserWithEmptyUsername(){
        return User.builder().username("").password("qwer").build();
    }

    private User createInvalidUserWithNullUsername(){
        return User.builder().username(null).password("qwer").build();
    }

    private User createInvalidUserWithEmptyPassword(){
        return User.builder().username("user").password("").build();
    }

    private User createInvalidUserWithIncorrectPassword(){
        return User.builder().username("user").password("qwer1").build();
    }
}