package com.example.bootandrest.service;

import com.example.bootandrest.entity.User;

import java.util.List;

public interface UserService {

     List<User> getAllUsers();

     void save(User user);

     void update(User user);

     void delete(Long id);

     User findUserByID(Long id);

     User findUserByEmail(String email);

     User findUserByName(String name);

     void processOAuthPostLogin(CustomOAuth2User username);

      void countAuth(Long id);

}
