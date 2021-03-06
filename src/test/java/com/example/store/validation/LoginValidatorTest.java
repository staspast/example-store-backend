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
    public void shouldValidateValidUser() throws Exception {
        User user = createValidUser();
        when(userDao.findByEmail("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.check(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getMessage(), "");
    }

    @Test
    public void shouldInvalidateInvalidUserWithEmptyUsername() throws Exception {
        User user = createInvalidUserWithEmptyUsername();
        ResponseEntity<RestResponse> responseEntity = loginValidator.check(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Email is empty User does not exist ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithNullUsername() throws Exception {
        User user = createInvalidUserWithNullUsername();
        ResponseEntity<RestResponse> responseEntity = loginValidator.check(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Email is empty User does not exist ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithEmptyPassword() throws Exception {
        User user = createInvalidUserWithEmptyPassword();
        when(userDao.findByEmail("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.check(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Password is empty Passwords mismatch ");
    }

    @Test
    public void shouldInvalidateInvalidUserWithIncorrectPassword() throws Exception {
        User user = createInvalidUserWithIncorrectPassword();
        when(userDao.findByEmail("user")).thenReturn(createValidUser());
        ResponseEntity<RestResponse> responseEntity = loginValidator.check(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Passwords mismatch ");
    }


    private User createValidUser(){
        return User.builder().email("user").password("qwer").build();
    }

    private User createInvalidUserWithEmptyUsername(){
        return User.builder().email("").password("qwer").build();
    }

    private User createInvalidUserWithNullUsername(){
        return User.builder().email(null).password("qwer").build();
    }

    private User createInvalidUserWithEmptyPassword(){
        return User.builder().email("user").password("").build();
    }

    private User createInvalidUserWithIncorrectPassword(){
        return User.builder().email("user").password("qwer1").build();
    }
}