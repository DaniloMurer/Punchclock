package com.danilojakob.m223.punchclock.service;

import com.danilojakob.m223.punchclock.domain.User;
import com.danilojakob.m223.punchclock.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * Repository to access data from service
     */
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get User by it's username
     * @param username {@link String} username of the user
     * @return {@link User}
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Add user to the database
     * @param user {@link User} user to add to the database
     */
    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }
 }
