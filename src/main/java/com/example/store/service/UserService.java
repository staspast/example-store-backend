package com.example.store.service;

import com.example.store.dto.RegisterRequestDto;
import com.example.store.mapper.StoreMapper;
import com.example.store.model.dao.UserDao;
import com.example.store.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;

@Service
public class UserService {

    @Autowired private UserDao userDao;
    @Autowired private StoreMapper storeMapper;

    public void register(RegisterRequestDto requestDto){
        User user = storeMapper.map(requestDto, User.class);
        userDao.save(user);
    }

    public String login(User login) throws ServletException {
        if (login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }
        String username = login.getUsername();
        String password = login.getPassword();

        User user = userDao.findByUsername(username);

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        return Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
    }

}
