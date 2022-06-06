package com.example.bootandrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private String lastname;

    private int age;

    @JsonProperty("roles")
    private Set<RoleDTO> rolesDTO = new HashSet<>();

}
