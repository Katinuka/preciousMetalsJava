package com.katinuka.preciousMetals.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.katinuka.preciousMetals.util.MapToStringConverter;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "users")
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String roles;

    @Convert(converter = MapToStringConverter.class)
    private Map<Currency, Double> balance;

    {
        balance = new LinkedHashMap<>();
        balance.put(Currency.USD, 0.0);
        balance.put(Currency.Gold, 0.0);
        balance.put(Currency.Silver, 0.0);
        balance.put(Currency.Platinum, 0.0);
        balance.put(Currency.Palladium, 0.0);
    }


    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public void setBalance(Currency currency, Double amount) {
        this.balance.put(currency, amount);
    }
}
