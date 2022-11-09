package com.roman.nett.service.impl;

import com.roman.nett.dto.UserDto;
import com.roman.nett.model.Status;
import com.roman.nett.model.entity.Role;
import com.roman.nett.model.entity.User;
import com.roman.nett.repository.RoleRepository;
import com.roman.nett.repository.UserRepository;
import com.roman.nett.security.jwt.JwtUser;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        //todo проверка нужно ли регестрировать
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsernameIgnoreCase(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }

    @Override
    public void editUser(JwtUser jwtUser, UserDto userDto) {
        log.warn("ОЧЕНЬ ВАЖНО id:{} email:{}", jwtUser.getId(), jwtUser.getEmail());

        var user = userRepository.updateUser(userDto, jwtUser.getUsername());



        /*
        Customer customerToUpdate = customerRepository.getOne(id);
        customerToUpdate.setName(customerDto.getName);
        customerRepository.save(customerToUpdate);
        * */

        //Это 2 запроса, не ок.
        /*repository
                .findById(user.getId()) // returns Optional<User>
                .ifPresent(user1 -> {
                    user1.setFirstname(user.getFirstname);
                    user1.setLastname(user.getLastname);

                    repository.save(user1);
                });*/
    }

    @Override
    public boolean existUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return false;
    }


}
