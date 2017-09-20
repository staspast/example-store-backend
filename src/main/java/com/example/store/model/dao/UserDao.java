package com.example.store.model.dao;

import com.example.store.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
