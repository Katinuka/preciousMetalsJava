package com.katinuka.preciousMetals.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.katinuka.preciousMetals.model.User;
import com.katinuka.preciousMetals.repository.UserRepository;
import com.katinuka.preciousMetals.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
