package com.example.bootandrest.controller;

import com.example.bootandrest.dto.MapperUtil;
import com.example.bootandrest.dto.RoleDTO;
import com.example.bootandrest.dto.UserDTO;
import com.example.bootandrest.entity.Role;
import com.example.bootandrest.entity.User;
import com.example.bootandrest.service.RoleService;
import com.example.bootandrest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminRestController {

    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new  ResponseEntity<>(MapperUtil.convertList(users, this::convertToUserDto), HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
       User user = userService.findUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("users/authority")
    public ResponseEntity<User> getAuthenticationUser(Principal principal){
       User user = userService.findUserByEmail(principal.getName());
        if (user == null){
            String[] name = principal.getName().split(" ");
            user = userService.findUserByName(name[0]);
        }
        userService.countAuth(user.getId());
        return new  ResponseEntity<>(user, HttpStatus.OK);
    }

    private UserDTO convertToUserDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        Set<Role> roleList =  user.getRoles();
        userDTO.setRolesDTO(MapperUtil.convertSet(roleList, this::convertToRoleDTO));
        return userDTO;
    }

    private RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }


}
