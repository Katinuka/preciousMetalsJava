package com.katinuka.preciousMetals.controller;

import com.katinuka.preciousMetals.model.Transaction;
import com.katinuka.preciousMetals.repository.TransactionRepository;
import com.katinuka.preciousMetals.repository.UserRepository;
import com.katinuka.preciousMetals.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Iterable<Transaction> findCurrentTransactions() {
        return findByUser(userService.getCurrentUser().getUsername());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Transaction> findByUser(@PathVariable String username) {
        var user = userRepository.findByUsername(username).orElse(null);
        return transactionRepository.findByUser(user);
    }
}
