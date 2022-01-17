package com.example.bootandrest.service;

import com.example.bootandrest.entity.Role;
import com.example.bootandrest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> findAllRoles() {
        return repository.findAll();
    }

}
