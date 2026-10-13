package org.example.market.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Integer isAdmin;
    private String phone;
    private BigDecimal walletBalance;
}
