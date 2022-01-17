package com.example.bootandrest.controller;

import com.example.bootandrest.entity.Role;
import com.example.bootandrest.entity.User;
import com.example.bootandrest.service.RoleService;
import com.example.bootandrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminRestController {

    private UserService userService;


    private RoleService roleService;

    public AdminRestController() {
    }

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return userService.findUserByID(id);
    }

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }
    @PatchMapping("/update")
    public User update(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);

    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.findAllRoles();
    }


}
