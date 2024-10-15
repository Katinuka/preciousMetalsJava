package com.katinuka.preciousMetals;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.katinuka.preciousMetals.model.Currency;
import com.katinuka.preciousMetals.model.User;
import com.katinuka.preciousMetals.repository.UserRepository;

@SpringBootApplication
public class PreciousMetalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreciousMetalsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            var user = new User("user", encoder.encode("password"), "ROLE_USER");
            user.setBalance(Currency.USD, 10000.0);
            users.save(user);

            var admin = new User("admin", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN");
            admin.setBalance(Currency.USD, 10000.0);
            users.save(admin);
        };
    }
}
