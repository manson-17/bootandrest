package com.example.bootandrest.controller;

import com.example.bootandrest.entity.Role;
import com.example.bootandrest.entity.User;
import com.example.bootandrest.service.RoleService;
import com.example.bootandrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


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
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new  ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
       User user = userService.findUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("users/authority")
    public ResponseEntity<User> getAuthorityUser(@AuthenticationPrincipal User user){
        User user1 = userService.findUserByEmail(user.getEmail());
        return new  ResponseEntity<>(user1, HttpStatus.OK);
    }


}
