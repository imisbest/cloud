package com.csw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class User implements Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private Date registerTime;
    private Integer status;

    private User user;

    public User(String id, String username, String password, String email, Date registerTime, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registerTime = registerTime;
        this.status = status;
    }
}
