package com.katinuka.preciousMetals.service;

import com.katinuka.preciousMetals.model.Action;
import com.katinuka.preciousMetals.model.Currency;
import com.katinuka.preciousMetals.model.Transaction;
import com.katinuka.preciousMetals.model.User;
import com.katinuka.preciousMetals.repository.TransactionRepository;
import com.katinuka.preciousMetals.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        return findByUsername(username);
    }

    @Transactional
    public void buyMetal(User user, Currency metal, double ounces, double pricePerOunce) {
        var balance = user.getBalance();
        var amountToPay = ounces * pricePerOunce;

        user.setBalance(metal, balance.get(metal) + ounces);
        user.setBalance(Currency.USD, balance.get(Currency.USD) - amountToPay);
        userRepository.save(user);
        saveTransaction(user, metal, Action.Buy, ounces, amountToPay);
    }

    @Transactional
    public void sellMetal(User user, Currency metal, double ounces, double pricePerOunce) {
        var balance = user.getBalance();
        var amountToGet = ounces * pricePerOunce;

        user.setBalance(metal, balance.get(metal) - ounces);
        user.setBalance(Currency.USD, balance.get(Currency.USD) + amountToGet);
        userRepository.save(user);
        saveTransaction(user, metal, Action.Sell, ounces, amountToGet);
    }

    private void saveTransaction(User user, Currency metal, Action action, double ounces, double totalPrice) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setMetal(metal);
        transaction.setAction(action);
        transaction.setOunces(ounces);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionTime(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}