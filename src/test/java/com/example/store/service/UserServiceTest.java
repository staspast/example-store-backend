package com.example.store.service;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import com.example.store.validation.LoginValidator;
import com.example.store.validation.RegistrationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock private UserDao userDao;
    @Mock private StoreMapper storeMapper;
    @Mock private RegistrationValidator registrationValidator;
    @Mock private LoginValidator loginValidator;
    @Spy @InjectMocks private UserService userService;

    private User user;
    private RegisterRequestDto requestDto;

    @Before
    public void setUp(){
        user = User.builder().email("user@us.com").password("user").build();
        requestDto = RegisterRequestDto.builder().email("user@us.com").password("user").passwordRepeat("user").build();
    }

    @Test
    public void shouldRegisterValidUser() throws Exception {
        when(registrationValidator.check(requestDto)).thenReturn(createValidResponse());
        when(storeMapper.map(requestDto, User.class)).thenReturn(user);
        userService.register(requestDto);

        verify(storeMapper).map(requestDto, User.class);
        verify(userDao).save(user);
    }

    @Test
    public void shouldNotRegisterInvalidUser() throws Exception {
        when(registrationValidator.check(requestDto)).thenReturn(createInvalidResponse());
        userService.register(requestDto);

        verify(storeMapper, never()).map(requestDto, User.class);
        verify(userDao, never()).save(user);
    }

    @Test
    public void shouldLoginValidUser() throws Exception {
        when(loginValidator.check(user)).thenReturn(createValidResponse());
        ResponseEntity<RestResponse> responseEntity = userService.login(user);

        assertTrue(responseEntity.getBody().getMessage().contains("Bearer"));
    }

    @Test
    public void shouldNotLoginInvalidUser() throws Exception {
        when(loginValidator.check(user)).thenReturn(createInvalidResponse());
        ResponseEntity<RestResponse> responseEntity = userService.login(user);

        assertFalse(responseEntity.getBody().getMessage().contains("Bearer"));
    }


    private ResponseEntity<RestResponse> createValidResponse(){
        return new ResponseEntity<>(new RestResponse(""), HttpStatus.OK);
    }

    private ResponseEntity<RestResponse> createInvalidResponse(){
        return new ResponseEntity<>(new RestResponse("message"), HttpStatus.BAD_REQUEST);
    }
}