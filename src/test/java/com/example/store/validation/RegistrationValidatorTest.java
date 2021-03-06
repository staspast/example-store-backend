package com.example.store.validation;

import com.example.store.dto.RegisterRequestDto;
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
public class RegistrationValidatorTest {

    @Mock
    private UserDao userDao;

    @Spy @InjectMocks
    private RegistrationValidator validator;

    @Test
    public void shouldValidateValidRegistrationRequest() throws Exception {
        RegisterRequestDto requestDto = createValidRegistrationRequest();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getMessage(), "");
    }

    @Test
    public void shouldInvalidateInvalidRegistrationRequestWithEmptyUsername() throws Exception {
        RegisterRequestDto requestDto = createInvalidRegistrationRequestWithEmptyUsername();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Email is empty ");
    }

    @Test
    public void shouldInvalidateInvalidRegistrationRequestWithNullUsername() throws Exception {
        RegisterRequestDto requestDto = createInvalidRegistrationRequestWithNullUsername();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Email is empty ");
    }

    @Test
    public void shouldInvalidateInvalidRegistrationRequestWithPasswordMismatch() throws Exception {
        RegisterRequestDto requestDto = createInvalidRegistrationRequestWithPasswordMismatch();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "Passwords mismatch ");
    }

    @Test
    public void shouldInvalidateValidRegistrationRequestThatAlreadyExists() throws Exception {
        when(userDao.findByEmail("johnny@edu.go")).thenReturn(User.builder().build());
        RegisterRequestDto requestDto = createValidRegistrationRequest();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "User already exists ");
    }

    @Test
    public void shouldInvalidateValidRegistrationRequestThatAlreadyExistsAndPasswordMismatch() throws Exception {
        when(userDao.findByEmail("johnny@edu.go")).thenReturn(User.builder().build());
        RegisterRequestDto requestDto = createInvalidRegistrationRequestWithPasswordMismatch();
        ResponseEntity<RestResponse> responseEntity = validator.check(requestDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "User already exists Passwords mismatch ");
    }





    //Private create test object methods

    private RegisterRequestDto createValidRegistrationRequest(){
        return RegisterRequestDto.builder().password("qwer").passwordRepeat("qwer").firstName("Jon").lastName("Snow").email("johnny@edu.go").build();
    }

    private RegisterRequestDto createInvalidRegistrationRequestWithEmptyUsername(){
        return RegisterRequestDto.builder().password("qwer").passwordRepeat("qwer").firstName("Jon").lastName("Snow").email("").build();
    }

    private RegisterRequestDto createInvalidRegistrationRequestWithNullUsername(){
        return RegisterRequestDto.builder().password("qwer").passwordRepeat("qwer").firstName("Jon").lastName("Snow").email(null).build();
    }

    private RegisterRequestDto createInvalidRegistrationRequestWithPasswordMismatch(){
        return RegisterRequestDto.builder().password("qwer").passwordRepeat("qwer1").firstName("Jon").lastName("Snow").email("johnny@edu.go").build();
    }
}