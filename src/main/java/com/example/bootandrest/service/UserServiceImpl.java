package com.example.bootandrest.service;


import com.example.bootandrest.entity.Role;
import com.example.bootandrest.entity.User;
import com.example.bootandrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {


    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findUserByID(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }


    @Override
    public void processOAuthPostLogin(CustomOAuth2User oAuth2User) {
        User existUser = userRepository.findUserByEmail(oAuth2User.getEmail());
        String[] name = oAuth2User.getName().split(" ");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role("USER_ROLE"));
        if (existUser == null) {
            User newUser = new User();
            newUser.setProvider(Provider.GOOGLE);
           // newUser.setRoles(roleSet);
            newUser.setEmail(oAuth2User.getEmail());
            newUser.setName(name[0]);
            newUser.setPassword("1000");
            newUser.setAge(35);
            newUser.setLastname(name[1]);

            userRepository.save(newUser);

            System.out.println("Created new user: " + newUser.getName());
        }

    }

}
