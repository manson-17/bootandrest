package com.example.bootandrest.service;


import com.example.bootandrest.entity.User;
import com.example.bootandrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void countAuth(Long id) {
         entityManager.createNativeQuery("""
                        insert into test.inpute_information(account_id, date)
                                     values (?, ?)
                                     on conflict (account_id, date) 
                                     do update set count = excluded.count + 1;
                                     """)
                .setParameter(1, id)
                .setParameter(2, ZonedDateTime.now(ZoneId.of("+3")))
                .executeUpdate();
        }

//                INSERT INTO test.inpute_information(account_id, date) 
//                VALUES (?, ?)
//                ON CONFLICT(account_id, date) DO 
//                UPDATE SET count = count + 1
//                """)
//                .setParameter(1, id)
//                .setParameter(2, ZonedDateTime.now(ZoneId.of("+3")))
//                .executeUpdate();

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
        if (user.getPassword().equals("")) {
            user.setPassword(findUserByID(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setProvider(findUserByID(user.getId()).getProvider());
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
        //Set<Role> roleSet = new HashSet<>();
        // roleSet.add(new Role("ROLE_USER"));
        if (existUser == null) {
            User newUser = new User();
            newUser.setProvider(Provider.GOOGLE);
            //  newUser.setRoles(roleSet);
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
