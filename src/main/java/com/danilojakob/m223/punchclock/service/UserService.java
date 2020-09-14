package com.danilojakob.m223.punchclock.service;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return {@link ApplicationUser}
     */
    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Add user to the database
     * @param applicationUser {@link ApplicationUser} user to add to the database
     */
    public void saveUser(ApplicationUser applicationUser) {
        userRepository.saveAndFlush(applicationUser);
    }

    public List<ApplicationUser> findAll() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
 }
