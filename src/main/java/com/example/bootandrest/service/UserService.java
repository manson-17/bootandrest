package com.example.bootandrest.service;

import com.example.bootandrest.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void save(User user);

    public void update(User user);

    public void delete(Long id);

    public User findUserByID(Long id);

    public User findUserByEmail(String email);

}
