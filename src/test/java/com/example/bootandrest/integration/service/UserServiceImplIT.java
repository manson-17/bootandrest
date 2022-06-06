package com.example.bootandrest.integration.service;


import com.example.bootandrest.integration.annatation.IT;
import com.example.bootandrest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceImplIT {

    public static final Long USER_ID = 1L;

    private final UserRepository userRepository;

    @Test
    void findUserByID() {

        var actualResult = userRepository.findUserById(USER_ID);

        assert(!actualResult.equals(null));
    }
}
